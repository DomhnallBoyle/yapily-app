module.exports = function(app) {
    var institutionController = require('../controllers/institution');

    app.route('/institutions')
        .get(institutionController.getInstitutions);

    app.route('/institutions/:id')
        .get(institutionController.getInstitution);
}