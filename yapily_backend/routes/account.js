module.exports = function(app) {
    var accountController = require('../controllers/account');

    app.route('/accounts')
        .post(accountController.initiateAccountAuthorisationRequest);

    app.route('/accounts')
        .patch(accountController.reauthoriseAccountRequest);

    app.route('/account-auth-redirect')
        .get(accountController.accountAuthRedirect);
}