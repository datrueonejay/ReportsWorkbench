const express = require('express')
const app = express()
const bodyParser = require('body-parser')
const session = require('express-session')
const Database = require('./database')
const http = require('http')

const PORT = 8000

// const crypto = require('crypto');
app.use(bodyParser.json())

app.use(session({
  secret: 'roshan kathir',
  resave: false,
  saveUninitialized: true
}))

const cookie = require('cookie')

app.use(express.static('static'))

// Commenting this out for the moment. Its not being used.
/*
var isAuthenticated = function (req, res, next) {
  if (!req.body.username) return res.status(401).end('access denied')
  next()
}
*/

app.get('/', function (req, res) {
  res.sendfile('dummy.html')
})

app.use(function (req, res, next) {
  var cookies = cookie.parse(req.headers.cookie || '')
  var cookiesUsername = (cookies.username) ? cookies.username : null
  req.session.username = cookiesUsername
  req.username = req.session.username
  console.log('HTTP request', req.query['username'], req.method, req.url, req.body)
  next()
})

app.post('/login/org-user/', function (req, res, next) {
  const reqUsername = req.body.username
  const reqPassword = req.body.password
  Database.getUser(reqUsername).then((result) => {
    const dbUser = result[0]
    if (dbUser === undefined) {
      res.status(400).send('Login Failed\n')
      return
    }
    if (!Authenticate(reqPassword, dbUser.Password)) {
      res.status(400).send('Login Failed\n  ')
      return
    }
    res.status(200).send('Login Successful\n')
  }).catch((err) => {
    console.log(err)
  })
})

function Authenticate (password, dbPassword) {
  // TODO Hash request password and pass in database
  return password === dbPassword
}

// status 500 = internal server error.
// status 404 = not found error.

/*
 * Will return an error if no user with the user name or return a JSON string with users credentials.
 */
app.get('/login/org-user/', function (req, res, next) {
  const reqUsername = req.query['username']
  Database.getUser(reqUsername).then((result) => {
    if (result[0] === undefined) {
      res.status(400).send('Account not found\n')
      return
    }
    res.send({ 'username': result[0].Username, 'password': result[0].Password })
  })
})

http.createServer(app).listen(PORT, function (err) {
  if (err) console.log(err)
  else console.log('HTTP server on http://localhost:%s', PORT)
})
