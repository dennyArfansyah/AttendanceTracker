const mongoose = require('mongoose');

const employeeSchema = mongoose.Schema({
    _id : mongoose.Types.ObjectId,
    email : { 
        type: String, 
        required: true,
        unique: true,
        match: /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/    
    },
    password : { type: String, required: true},
    name: { type : String, required : true},
    dob: { type: Date, required: true },
    division: { type : String, required : true}
});

module.exports = mongoose.model('Employee', employeeSchema);
