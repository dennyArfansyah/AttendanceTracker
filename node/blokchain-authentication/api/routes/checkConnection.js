const express = require('express');
const router = express.Router();

router.get('/', (req, res, next) => {
    res.status(200).json({
        status : "success",
        message : "Congratulation, you can continue to use this app."
        ,data : {
            "divisions" : [
                {
                    "id" : "1",
                    "name" : "HR",
                },
                {
                    "id" : "2",
                    "name" : "Marketing",
                },
                {
                    "id" : "3",
                    "name" : "Accounting",
                },
                {
                    "id" : "4",
                    "name" : "IT",
                }
            ],"leave_types" : [
                {
                    "id" : "1",
                    "name" : "Annual",
                },
                {
                    "id" : "2",
                    "name" : "Childrecare",
                },
                {
                    "id" : "3",
                    "name" : "Infant Care",
                },
                {
                    "id" : "4",
                    "name" : "Maternity",
                },
                {
                    "id" : "5",
                    "name" : "Patternity",
                },
                {
                    "id" : "6",
                    "name" : "Sick",
                } 
            ]
        }
    });
});

module.exports = router;