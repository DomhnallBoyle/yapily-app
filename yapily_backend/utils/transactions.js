var yapilyApi = require('yapily_api');
var yapilyAuth = require('./yapilyAuth');

// background function to get transactions
exports.getTransactions = function getTransactions() {
    console.log("Getting transactions...");

    User.find({}, function(error, users) {
        if (error) {
            console.log(error);
            return;
        }

        yapilyAuth();

        var consentsApi = new yapilyApi.ConsentsApi();
        var accountsApi = new yapilyApi.AccountsApi();
        var transactionsApi = new yapilyApi.TransactionsApi();

        users.map(function(user) {
            // if consent tokens available on user object
            user.consentIds.forEach(function(consentId) {
                consentsApi.getConsentByIdUsingGET(consentId, function(error, consent) {
                    if (error) {
                        console.log(error);
                    } else {
                        var status = consent.data.status;
                        var consentToken = consent.data.consentToken;

                        console.log(consent.data);

                        if (status == 'AUTHORIZED') {
                            accountsApi.getAccountsUsingGET(consentToken, function(error, accounts) {
                                if (error) {
                                    console.log(error);
                                } else {
                                    var accounts = accounts.data;

                                    // TODO: Fails here on monzo 
                                    // Requires in-app grant access before using the consent token
                                    // even though consent token == AUTHORISED

                                    accounts.forEach(function(account) {
                                        transactionsApi.getTransactionsUsingGET(consentToken, account.id, null, function(error, transactions) {
                                            if (error) {
                                                console.log(error);
                                            } else {
                                                var transactions = transactions.data;
                                                transactions.forEach(function(transaction) {
                                                    if (transaction.amount < 0) {
                                                        console.log(`${user.username} spent ${transaction.amount} on "${transaction.description}"`);
                                                    }
                                                });
                                            }
                                        });
                                    });
                                }
                            });
                        } else {
                            // TODO: Consent Not Authorized
                        }
                    }
                });
            });
        });
    });
}