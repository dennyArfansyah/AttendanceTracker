const mongoose = require('mongoose');

const approvalSchema = mongoose.Schema({
    _id : mongoose.Types.ObjectId,
    employeeId: { type: String, required: true},
    type: { type: String, required: true }, 
    status: { type: Number, required: true },
    reason: { type : String, required : true},
    since: {type: Date, required: true},
    until: { type: Date, required: true}
});

module.exports = mongoose.model('Approval', approvalSchema);
