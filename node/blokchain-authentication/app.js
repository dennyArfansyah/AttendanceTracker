const express = require('express');
const app = express();
const morgan = require('morgan');
const bodyParser = require('body-parser')
const mongoose = require('mongoose')

const employeeRoutes = require('./api/routes/employee');
const approvalRoutes = require('./api/routes/approval');
const feedRoutes = require('./api/routes/feed');
const welcomeRoutes = require('./api/routes/checkConnection');

mongoose.connect('mongodb://denny:' +  process.env.DB_PASSWORD + '@blockhain-auth-shard-00-00-fk7hy.mongodb.net:27017,blockhain-auth-shard-00-01-fk7hy.mongodb.net:27017,blockhain-auth-shard-00-02-fk7hy.mongodb.net:27017/test?ssl=true&replicaSet=blockhain-auth-shard-0&authSource=admin&retryWrites=true', {
    userMongoClient: true
});

app.use(morgan('dev'));
app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());

app.use('/welcome', welcomeRoutes);
app.use('/employees', employeeRoutes);
app.use('/approvals', approvalRoutes);
app.use('/feeds', feedRoutes);

app.use((req, res, next) =>{
    const error = new Error('Not found');
    error.status = 404;
    next(error);
});

app.use((error, req, res, next) => {
    res.status(error.status || 500);
    res.json({
        error:{
            message : error.message
        }
    });
});

module.exports = app;