const express = require('express');
const router = express.Router();
const Employee = require('../models/employee');
const mongoose = require('mongoose');
const checkAuth = require('../middleware/checkAuth');
const bcrypt = require('bcrypt');
const jwt = require('jsonwebtoken');
const fs   = require('fs');
var privateKey = fs.readFileSync('key/private.key', 'utf8');

// SignUp Employee
router.post('/signup', (req, res, next) => {
    Employee.findOne({ email : req.body.email})
        .exec()
        .then(employee => {
            if(employee){
                return res.status(409).json({
                    status : 'error',
                    message : "Email already exist"
                });
            }else {
                bcrypt.hash(req.body.password, 10, (err, hash) => {
                    if(err){
                        return res.status(500).json({
                            status : 'error 1',
                            error : err
                        });
                    }else {
                        const employee = new Employee({
                            _id : new mongoose.Types.ObjectId(),
                            name : req.body.name,
                            email : req.body.email,
                            password: hash,
                            dob: req.body.dob,
                            division : req.body.division
                        });
                        employee.save()
                            .then(result => {
                                console.log(result);
                                res.status(200).json({
                                    status : "success",
                                    message : 'Signed Up successfully',
                                    data : {
                                        email : result.email,
                                        password : result.password,
                                        name: result.name,
                                        division: result.division,
                                        dob: result.dob,
                                        id : result._id
                                    }
                                });
                            })
                            .catch(err => {
                                console.log(err);
                                res.status(500).json({
                                    status : "error",
                                    data : err
                                });
                            });
                    }
                });
            }
        })
        .catch(err => {
            console.log(err);
            res.status(500).json({
                status : "error 2",
                data : err
            });
        });
});

// Get All Employee
router.get('/', checkAuth, (req, res, next) => {
    Employee.find()
        .select('_id email password name dob division')
        .exec()
        .then(result => {
            console.log(result);
            if(result){
                res.status(200).json({
                    status : "success",
                    count : result.length,
                    data : result    
                });
            }else {
                res.status(200).json({
                    status : "error",
                    message : "No employee found"
                });
            }
        })
        .catch(err => {
            res.status(500).json({
                status : "error",
                data : err
            });
        });
});

// Get An Employee
router.get('/:employeeId', (req, res, next) => {
    const id = req.params.employeeId;
    Employee.findById(id)
    .select('_id email name dob division')
    .exec()
    .then(result => {
        console.log(result);
        if(result){
            res.status(200).json({
                status : "success",
                data : result    
            });
        }else {
            res.status(200).json({ 
                status : "error",
                message : "No employee found"
            });
        }
    })
    .catch(err => {
        console.log(err);
        res.status(500).json({
            status : "error",
            data : err
        });
    });
});

// SignIn Employee
router.post('/signin', (req, res, next) => {
    Employee.find({ email : req.body.email })
        .exec()
        .then(employee => {
            if(employee.length < 1){
                return res.status(401).json({
                    status : 'error',
                    message : "Authentication failed"
                });
            }

            console.log(employee[0].password)
            bcrypt.compare(req.body.password, employee[0].password, (err, result) => {
                if(err){
                    return res.status(401).json({
                        status : 'error',
                        message : "Authentication failed"
                    });
                }
                if(result){
                    var signOptions = {
                        issuer: "Blockchain Authentication",
                        subject: employee[0].email,
                        audience: "http://thisapi.auth.com/",
                        algorithm: "RS256",
                        expiresIn : '1d' 
                    };
                    var payload = {};
                    const token = jwt.sign(payload, privateKey, signOptions);
                    return res.status(200).json({
                        status : 'success',
                        message : "SignIn successfull",
                        data : {
                            _id : employee[0]._id,
                            name : employee[0].name,
                            email : employee[0].email,
                            password : employee[0].password,
                            dob : employee[0].dob,
                            division : employee[0].division,
                            token : token
                        }
                    });
                }
                res.status(401).json({
                    status : 'error',
                    message : "Authentication failed"
                });
            });
        })
        .catch(err => {
            console.log(err);
            res.status(500).json({
                status : "error",
                data : err
            });
        });
});

// Update A Employee
router.patch('/:employeeId', checkAuth, (req, res, next) => {
    const id = req.params.employeeId;
    const updateOps = {};
    for(const ops of req.body){
        updateOps[ops.propName] = ops.value;
    }
    Employee.update({ _id : id }, { $set: updateOps })
    .exec()
    .then(result => {
        console.log(result);
        if(result.n == 1){
            res.status(200).json({
                status : "success",
                message : 'Updated employee successfully',
            });
        }else {
            res.status(200).json({
                status : "error",
                message : 'Updated employee failed'
            });
        }
    })
    .catch(err => {
        console.log(err);
        res.status(500).json({
            status : "error",
            data : err
        });
    });
});

router.get('/token/:email', (req, res, next) => {
    var signOptions = {
        issuer: "Blockchain Authentication",
        subject: req.params.email,
        audience: "http://thisapi.auth.com/",
        algorithm: "RS256",
        expiresIn : '1d' 
    };
    var payload = {};
    const token = jwt.sign(payload, privateKey, signOptions);
    res.status(200).json({
        status : 'success',
        message : token
    });
});

module.exports = router;
