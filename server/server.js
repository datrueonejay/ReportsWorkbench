const path = require('path');
const express = require('express');
const app = express();

const bodyParser = require('body-parser');
const session = require('express-session');
//const crypto = require('crypto');
app.use(bodyParser.json());

const Datastore = require('nedb');
var users = new Datastore({ filename: 'db/users.db', autoload: true });

app.use(session({
    secret: 'roshan kathir',
    resave: false,
    saveUninitialized: true,
}));

const cookie = require('cookie');

app.use(express.static('static'));

var LoginCredentials = function item(password, username)
{
    this.password = password;
    this.username = username;
}

var isAuthenticated = function(req, res, next) {
    if (!req.body.username) return res.status(401).end("access denied");
    next();
};

app.get('/', function (req, res) {
  res.sendfile("dummy.html");
});

app.use(function (req, res, next){
    var cookies = cookie.parse(req.headers.cookie || '');
    var cookiesUsername = (cookies.username)? cookies.username : null;
    req.session.username = cookiesUsername;
    req.username = req.session.username;
    console.log("HTTP request", req.username, req.method, req.url, req.body);
    next();
});

app.post('/login/org-user/', function (req, res, next) {
    var logCred = new LoginCredentials(req.param('pass') , req.param('username'));
    users.insert(logCred, function (err, message) {
        if (err) return res.status(500).end(err);
        return res.json(message);
    });
});


// status 500 = internal server error.
// status 404 = not found error.

/*
 * Will return an error if no user with the user name or return a JSON string with users credentials.
 */
app.get('/login/get-org-user/', function (req, res, next) {
    users.findOne({username: req.params.username}, function(err, user){
        if (err) return res.status(500).end(err);
        if (!user) return res.status(404).end("Username" + req.params.username + " does not exists");
        if(req.session.username == user.username){
                 res.json(user);
        }
        else
        {
            return res.status(401).end("can't perform the desired action.");
        }
    }); 
});

const http = require('http');
const PORT = 8000;

http.createServer(app).listen(PORT, function (err) {
    
    if (err) console.log(err);
    else console.log("HTTP server on http://localhost:%s", PORT);
});