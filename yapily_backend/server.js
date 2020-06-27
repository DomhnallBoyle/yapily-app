var bodyParser = require('body-parser');
var config = require('./config')
var express = require('express');
var mongoose = require('mongoose');
var transactions = require('./utils/transactions');

var app = express();
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));
app.engine('html', require('ejs').renderFile);
app.set('views', './static/html');

// setup models + mongodb
mongoose.Promise = global.Promise;
mongoose.connect('mongodb://localhost:27017/yapily_app_db', {useNewUrlParser: true});
for (var model of ['user']) {
    require('./models/' + model);
}

// setup routes
for (var routes of ['account', 'institution', 'user']) {
    routes = require('./routes/' + routes);
    routes(app);
}

// start app
app.listen(config.PORT, function() {
    console.log(`App listening on port ${config.PORT}`);

    // function runs every 10 seconds
    setInterval(transactions.getTransactions, 10000);
});