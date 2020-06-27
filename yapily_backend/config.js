require('dotenv').config({path: `variables.env`});

var config = {
    PORT: process.env.PORT,
    YAPILY_APP_ID: process.env.YAPILY_APP_ID,
    YAPILY_APP_SECRET: process.env.YAPILY_APP_SECRET
};

module.exports = config;