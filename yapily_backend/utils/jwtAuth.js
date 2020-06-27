var config = require('../config');
var jwt = require('jsonwebtoken');

// get a new jwt token
module.exports.sign = function(signage) {
    var token = jwt.sign({signage}, config.YAPILY_APP_SECRET, {
        expiresIn: 10 // seconds
    });

    return token;
}

// verify a JWT token
module.exports.verify = function(token, response, callback) {
    jwt.verify(token, config.YAPILY_APP_SECRET, function(error, decoded) {
        if (error) {
            response.status(401).json({message: 'Failed to authenticate token.'});
        } 
        else {
            callback();
        }
    });
};