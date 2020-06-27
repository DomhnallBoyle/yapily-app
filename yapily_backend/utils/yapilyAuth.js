
var config = require('../config');
var yapilyClient = require('../utils/yapilyClient')

module.exports = function() {
    var auth = yapilyClient.authentications['basicAuth'];
    
    auth.username = config.YAPILY_APP_ID;
    auth.password = config.YAPILY_APP_SECRET;

    return auth;
};