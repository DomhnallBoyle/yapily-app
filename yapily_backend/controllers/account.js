var jwtAuth = require('../utils/jwtAuth');
var yapilyApi = require('yapily_api');
var yapilyAuth = require('../utils/yapilyAuth');

// account authorisation - retrieves institution auth url
exports.initiateAccountAuthorisationRequest = function(request, response) {
    jwtAuth.verify(request.headers.token, response, function() {
        yapilyAuth();

        var accountAuthRequest = new yapilyApi.AccountAuthorisationRequest();
        accountAuthRequest.institutionId = request.body.institutionId;
        accountAuthRequest.applicationUserId = request.body.applicationUserId;
        accountAuthRequest.callback = request.body.callbackUrl;

        var accountsApi = new yapilyApi.AccountsApi();
        accountsApi.initiateAccountRequestUsingPOST(accountAuthRequest, function(error, data) {
            if (error) {
                return response.status(error.status).json({error});
            }
            response.status(200).send(data.data);
        });
    });
};

// reauthorise account authorisation using a consent token
exports.reauthoriseAccountRequest = function(request, response) {
    jwtAuth.verify(request.headers.token, response, function() {
        yapilyAuth();

        var applicationUserId = request.body.applicationUserId;
    
        User.findOne({_id: applicationUserId}, function(error, user) {
            if (error) {
                return response.status(error.status).json({error});
            }
    
            if (!user) {
                return response.status(404).json({message: 'User not found.'});
            }
    
            var consentsApi = new yapilyApi.ConsentsApi();
            var accountsApi = new yapilyApi.AccountsApi();
            consentsApi.getConsentByIdUsingGET(user.consentId, function(error, consent) {
                if (error) {
                    return response.status(error.status).json({error});
                }

                accountsApi.reAuthoriseAccountUsingPATCH(consent.data.consentToken, function(error, data) {
                    if (error) {
                        return response.status(error.status).json({error});
                    }
                    response.status(200).send(data.data);
                });
            });
        });
    });
};

// redirected here from Yapily after successful connected account request
exports.accountAuthRedirect = function(request, response) {
    var yapilyUserId = request.query['user-uuid'];
    var applicationUserId = request.query['application-user-id'];

    // update the user's consents
    var consentsApi = new yapilyApi.ConsentsApi();
    consentsApi.getUserConsentsUsingGET(yapilyUserId, null, function(error, consents) {
        if (error) {
            return response.status(error.status).json({error});
        }

        // get all consent ids
        var consentIds = [];
        consents.forEach(function(consent) {
            consentIds.push(consent.id);
        });
        
        // update user in database
        User.findOneAndUpdate({_id: applicationUserId}, {yapilyId: yapilyUserId, consentIds}, function(error, user) {
            if (error) {
                return response.status(error.status).json({error});
            }
    
            if (!user) {
                return response.status(404).json({message: 'User not found.'});
            }
            
            response.render('accountAuthRedirect.html');
        });
    });
};
