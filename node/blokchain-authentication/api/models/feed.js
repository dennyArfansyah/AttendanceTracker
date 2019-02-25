const mongoose = require('mongoose');

const feedSchema = mongoose.Schema({
    _id : mongoose.Types.ObjectId,
    imageUrl : { type: String, required: true},
    content: { type: String, required: true }, 
    date: { type: Date, required: true },
});

module.exports = mongoose.model('Feed', feedSchema);
