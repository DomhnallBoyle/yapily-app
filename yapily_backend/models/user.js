var mongoose = require('mongoose');
var uniqueValidator = require('mongoose-unique-validator')
var uuid = require('uuid');

var UserSchema = new mongoose.Schema({
    _id: { 
        type: String, 
        default: uuid.v1 
    },
    username: {
        type: String,
        required: true,
        unique: true,
    },
    yapilyId: {
        type: String, 
        unique: true
    },
    consentIds: [{
        type: String, 
        unique: true
    }]
});
UserSchema.plugin(uniqueValidator);

module.exports = mongoose.model('User', UserSchema);
