var jwtAuth = require('../utils/jwtAuth');
var yapilyApi = require('yapily_api');
var yapilyAuth = require('../utils/yapilyAuth');

// get all institutions
exports.getInstitutions = function(request, response) {
    jwtAuth.verify(request.headers.token, response, function() {
        yapilyAuth();

        var institutionsApi = new yapilyApi.InstitutionsApi();
        institutionsApi.getInstitutionsUsingGET(function(error, institutions) {
            if (error) {
                return response.status(error.status).json({error});
            }
            response.status(200).json(institutions.data);
        });
    });
};

// get institution by id
exports.getInstitution = function(request, response) {
    jwtAuth.verify(request.headers.token, response, function() {
        yapilyAuth();

        var institutionId = request.params.id;
    
        var institutionsApi = new yapilyApi.InstitutionsApi();
        institutionsApi.getInstitutionUsingGET(institutionId, function(error, institution) {
            if (error) {
                return response.status(error.status).json({error});
            }
            response.status(200).json(institution);
        });
    });
};