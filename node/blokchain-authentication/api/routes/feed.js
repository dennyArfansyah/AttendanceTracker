const express = require('express');
const router = express.Router();
const Feed = require('../models/feed');
const mongoose = require('mongoose');
const bcrypt = require('bcrypt');
const jwt = require('jsonwebtoken');
const checkAuth = require('../middleware/checkAuth');

// Get All Feed
router.get('/', checkAuth, (req, res, next) => {
    Feed.find()
        .select('_id imageUrl content date')
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
                    message : "No feed found"
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

// Save Feed
router.post('/', checkAuth, (req, res, next) => {
    const feed = new Feed({
        _id : new mongoose.Types.ObjectId(),
        imageUrl : req.body.imageUrl,
        content: req.body.content,
        date: req.body.date
    });

    feed
        .save()
        .then(result => {
            console.log(result);
            res.status(200).json({
                status : "success",
                message : 'Added feed successfully',
                data : {
                    date : feed.date,
                    content : feed.content,
                    date: feed.date,
                    id : feed._id
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
});

module.exports = router;