var mongoose = require('mongoose');
var jwtAuth = require('../utils/jwtAuth');
var uuidValidate = require('uuid-validate');
var yapilyApi = require('yapily_api');
var yapilyAuth = require('../utils/yapilyAuth');

User = mongoose.model('User');

// create a new user
exports.createUser = function(request, response) {
    // check if user exists already
    User.findOne({username: request.body.username}, function(error, user) {
        if (user)
            return response.status(409).json({message: 'Username already taken.'});
        else {
            // save user if username not taken
            new User(request.body).save(function(error, user) {      
                if (error)
                    return response.status(error.status).json({error}) 

                var token = jwtAuth.sign(user.username);

                response.status(201).json({id: user._id, token});
            });
        }
    });
};

// login with a username only
exports.login = function(request, response) {
    var username = request.body.username;

    User.findOne({username}, function(error, user) {
        if (error) {
            return response.status(error.status).json({error});
        }

        if (!user) {
            return response.status(404).json({message: 'User not found.'});
        }

        var token = jwtAuth.sign(username);

        response.status(200).json({id: user._id, token});
    });
}

// get user by id
exports.getUser = function(request, response) {
    jwtAuth.verify(request.headers.token, response, function() {
        var userId = request.params.id;

        if (uuidValidate(userId))
            query = {_id: userId};
        else
            query = {username: userId};
    
        User.findOne(query, function(error, user) {
            if (error) {
                return response.status(error.status).json({error});
            }
    
            if (!user) {
                return response.status(404).json({message: 'User not found.'});
            }
    
            response.status(200).end(JSON.stringify(user, null, 4));
        });
    });
}

exports.deleteUsers = function(request, response) {
    User.deleteMany({}, function(error, data) {
        if (error) {
            return response.status(error.status).json({error});
        }
        response.status(200).json({message: "User's removed successfully."});
    });
}

exports.hasConnectedAccounts = function(request, response) {
    jwtAuth.verify(request.headers.token, response, function() {
        var applicationUserId = request.params.id;

        User.findOne({_id: applicationUserId}, function(error, user) {
            if (error) {
                return response.status(error.status).json({error});
            }
    
            if (!user) {
                return response.status(404).json({message: 'User not found.'});
            }

            var connected;
            if (user.consentIds.length > 0)
                connected = true;
            else 
                connected = false;

            response.status(200).json({connected});
        });
    });
}

exports.getAccounts = function(request, response) {
    jwtAuth.verify(request.headers.token, response, function() {
        yapilyAuth();

        var applicationUserId = request.params.id;

        User.findOne({_id: applicationUserId}, function(error, user) {
            if (error) {
                return response.status(error.status).json({error});
            }
            if (!user) {
                return response.status(404).json({message: 'User not found.'});
            }

            var consentsApi = new yapilyApi.ConsentsApi();
            var accountsApi = new yapilyApi.AccountsApi();

            var responseData = [];
            var consentIds = user.consentIds;

            if (consentIds.length > 0) {
                var counter = 0;

                consentIds.forEach(function(consentId) {
                    consentsApi.getConsentByIdUsingGET(consentId, function(error, consent) {
                        if (error) {
                            return response.status(error.status).json({error});
                        }

                        if (consent.data.status == 'REVOKED') {
                            // remove consent from user and update db
                            var index = consentIds.indexOf(consentId);
                            if (index > -1) {
                                consentIds.splice(index, 1);

                                User.update({_id: user._id}, {consentIds}, function(error, user) {
                                    if (error) {
                                        return response.status(error.status).json({error});
                                    } 

                                    if (counter == consentIds.length) {
                                        response.status(200).json(responseData);
                                    }
                                });
                            }
                        } 
                        else {
                            accountsApi.getAccountsUsingGET(consent.data.consentToken, function(error, accounts) {
                                if (error) {
                                    return response.status(error.status).json({error});
                                }
            
                                accounts.data.forEach(function(account) {
                                    account['consentId'] = consentId;
                                    account['status'] = consent.data.status;
                                    responseData.push(account);
                                });
        
                                if (++counter == consentIds.length) {
                                    response.status(200).json(responseData);
                                }
                            });
                        }
                    });
                });
            } else {
                response.status(200).json(responseData);
            }
        });
    });
}

exports.deleteConsent = function(request, response) {
    jwtAuth.verify(request.headers.token, response, function() {
        yapilyAuth();

        var applicationUserId = request.params.applicationUserId;
        var consentId = request.params.consentId;

        var consentsApi = new yapilyApi.ConsentsApi();
        consentsApi.deleteUsingDELETE(consentId, {forceDelete: true}, function(error, deleteResponse) {
            if (error) {
                return response.status(error.status).json({error});
            }

            User.findOne({_id: applicationUserId}, function(error, user) {
                if (error) {
                    return response.status(error.status).json({error});
                }
        
                if (!user) {
                    return response.status(404).json({message: 'User not found.'});
                }
    
                // update user's consent ids
                var consentIds = user.consentIds;
                var index = consentIds.indexOf(consentId);
                if (index > -1) {
                    consentIds.splice(index, 1);
                }

                User.update({_id: user._id}, {consentIds}, function(error, user) {
                    if (error) {
                        return response.status(error.status).json({error});
                    } 

                    response.status(200).json({message: deleteResponse.data.deleteStatus});
                });
            });
        });
    });
}

exports.getConsent = function(request, response) {
    jwtAuth.verify(request.headers.token, response, function() {
        yapilyAuth();

        var consentId = request.params.consentId;

        var consentsApi = new yapilyApi.ConsentsApi();
        consentsApi.getConsentByIdUsingGET(consentId, function(error, consent) {
            if (error) {
                return response.status(error.status).json({error});
            }
    
            response.status(200).json(consent.data);
        });
    });
}