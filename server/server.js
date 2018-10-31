const express = require('express');
const app = express();
const bodyParser = require('body-parser');
const session = require('express-session');
const Database = require('./database');
const http = require('http');
const crypto = require('crypto');

const PORT = 8000;

// const crypto = require('crypto');
app.use(bodyParser.json());

app.use(session({
  secret: 'roshan kathir',
  resave: false,
  saveUninitialized: true
}));

const cookie = require('cookie');

app.use(express.static('static'));

var MongoClient = require('mongodb').MongoClient;
var url = 'mongodb+srv://Team25:7H8Gh7re6S@team25-dsrl7.mongodb.net';
var ObjectId = require('mongodb').ObjectID;

var db; 

MongoClient.connect(url, function(err, database) {
  if(err) throw err;

  db = database.db("main");

  db.createCollection("users", function(err, res) {
    if (err) throw err;
  });
   
  
});



// Commenting this out for the moment. Its not being used.
/*
var isAuthenticated = function (req, res, next) {
  if (!req.body.username) return res.status(401).end('access denied')
  next()
}
*/

app.get('/', function (req, res) {
  res.sendfile('dummy.html')
});

app.use(function (req, res, next) {
  var cookies = cookie.parse(req.headers.cookie || '');
  var cookiesUsername = (cookies.username) ? cookies.username : null;
  req.session.username = cookiesUsername;
  req.username = req.session.username;

  

  console.log('HTTP request', req, req.method, req.url);
  next();
});

/*
  app.post('/reports/new-report/', function (req, res, next) {
  const reportTemplateType = Object.keys(req.body)[0]
  const reportData = req.body[reportTemplateType]

  for (const row in reportData) {
    reportData._id = row
    Database.getDatabaseRoot().collection(reportTemplateType).updateOne({ _id: row }, {$set: reportData[row]}, { upsert: true }, function (err, report) {
      if (err) return res.status(500).end(err)
    })
  }
  res.status(200).send('{}')
})

*/

function User(username, password, salt) {
    this.password = password;
    this.username = username;
    this.salt = salt;
}


app.post('/login/user-signup/', function (req, res, next) {
  const reqUsername = req.body.username;
  const reqPassword = req.body.password;

  var salt = crypto.randomBytes(16).toString('base64');
  var hash = crypto.createHmac('sha512', salt);
  hash.update(reqPassword);
  var hashedPassword = hash.digest('base64');

  console.log("The username is: " + reqUsername);
  console.log("The password is: " + reqPassword);
  console.log("The hashed password is: " + hashedPassword);
  console.log("The value of salt is: " + salt);
  
  var newUser = User(reqUsername, hashedPassword, salt);

  var userJson = JSON.stringify(newUser); 

  db.collection("users").findOne({Username : reqUsername}, function(err, user) 
  {
    console.log("The value of user is: " + user);
    if(err) return res.status(500).end(err);
    if(user != null) return res.status(409).end("User with the name (" + reqUsername + ") already exists");
    db.collection("users").insert({Username : reqUsername, Password : hashedPassword, salt : salt}, function (error, newAddedUser) {
      if (error) return res.status(500).end(error);
    
      return "{success : true}"; 
    });
  });
});

// status 500 = internal server error.
// status 404 = not found error.

/*
 * Will return an error if no user with the user name or return a JSON string with users credentials.
 */
app.get('/login/org-user/', function (req, res, next) {
  const reqUsername = req.body.username;
  const reqPassword = req.body.password;

  console.log("The value of the request username is: " + reqUsername);
  console.log("The value of the request password is: " + reqPassword);
  // retrieve the user info from the database.
  db.collection("users").findOne({Username : reqUsername},  function(err, user) { 
        if (err) return res.status(500).end(err);
        if (!user) 
          return res.status(401).end("access denied \n");

        if(user == null)
        {
          console.log("The value of user is null.");
        }

        var salt = user.salt;
        var hash = crypto.createHmac('sha512', salt);
        hash.update(reqPassword);
        var hashedPassword = hash.digest('base64');

        console.log("The value of the password on the database is: " + user.Password);
        console.log("The value of the password in request is: " + hashedPassword);

        if(user.Password !== hashedPassword){
          console.log("LOGIN STATUS: The login was unsuccessful.");
          return res.status(401).end("access denied"); 
        }
        else 
        {
          console.log("LOGIN STATUS: The login was successful.");
          return res.status(200).end("{authenticated :}");
        }
  });
});



http.createServer(app).listen(PORT, function (err) {
  if (err) console.log(err);
  else console.log('HTTP server on http://localhost:%s', PORT);
});

app.get('/reports/get-report-data/', function (req, res, next) {
  Database.getDatabaseRoot().collection('reports')
    .find({ author: req.reportId })
    .sort({ $natural: 1 })
    .toArray(function (err, report) {
      if (err) return res.status(500).end(err)
      return res.json(report);
    });
});

app.post('/reports/new-report/', function (req, res, next) {
  const reportTemplateType = Object.keys(req.body)[0]
  const reportData = req.body[reportTemplateType]

  for (const row in reportData) {
    reportData._id = row
    Database.getDatabaseRoot().collection(reportTemplateType).updateOne({ _id: row }, {$set: reportData[row]}, { upsert: true }, function (err, report) {
      if (err) return res.status(500).end(err)
    });
  }
  res.status(200).send('{}')
});
