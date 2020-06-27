module.exports = function(app) {
    var userController = require('../controllers/user')

    app.route('/login')
        .post(userController.login);

    app.route('/users')
        .post(userController.createUser);

    app.route('/users')
        .delete(userController.deleteUsers);

    app.route('/users/:id')
        .get(userController.getUser);

    app.route('/users/:id/connected')
        .get(userController.hasConnectedAccounts);
        
    app.route('/users/:applicationUserId/consents/:consentId')
        .get(userController.getConsent);

    app.route('/users/:applicationUserId/consents/:consentId')
        .delete(userController.deleteConsent);

    app.route('/users/:id/accounts')
        .get(userController.getAccounts);
}