const express = require('express');
const router = express.Router();
const Approval = require('../models/approval');
const Employee = require('../models/employee');
const mongoose = require('mongoose');
const checkAuth = require('../middleware/checkAuth');

// Get All Approval
router.get('/:employeeId', checkAuth, (req, res, next) => {
    const employeeId = req.params.employeeId;
    Approval.find({employeeId : employeeId})
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
                    message : "No approval found"
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

// Apply Approval
router.post('/:employeeId', checkAuth, (req, res, next) => {
    const employeeId = req.params.employeeId;
    Employee.findById(employeeId)
    .exec()
    .then(result => {
        console.log(result);
        if(result){
            const approval = new Approval({
                _id : new mongoose.Types.ObjectId(),
                employeeId : req.body.employeeId,
                type: req.body.type,
                status : req.body.status,
                reason: req.body.reason,
                since: req.body.since,
                until: req.body.until
            });
        
            approval
                .save()
                .then(resultApproval => {
                    console.log(resultApproval);
                    res.status(200).json({
                        status : "success",
                        message : 'Added approval successfully',
                        data : {
                            type : approval.type,
                            status : approval.status,
                            reason: approval.reason,
                            since :approval.since,
                            until: approval.until,
                            id : approval._id
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

// Update A Approval
router.patch('/:approvalId', checkAuth, (req, res, next) => {
    const id = req.params.approvalId;
    const updateOps = {};
    for(const ops of req.body){
        updateOps[ops.propName] = ops.value;
    }
    Approval.update({ _id : id }, { $set: updateOps })
    .exec()
    .then(result => {
        console.log(result);
        if(result.n == 1){
            res.status(200).json({
                status : "success",
                message : 'Updated approval successfully',
            });
        }else {
            res.status(200).json({
                status : "error",
                message : 'Updated approval failed'
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

// Delete An Approval
router.delete('/:approvalId', checkAuth, (req, res, next) => {
    const id = req.params.approvalId;
    Approval.remove({_id : id})
    .exec()
    .then(result => {
        console.log(result);
        if(result.n == 1){
            res.status(200).json({
                status : "success",
                message : 'Deleted approval successfully',
            });
        }else {
            res.status(200).json({
                status : "error",
                message : 'No approval found'
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

module.exports = router;