const express = require('express')
const app = express()
const bodyParser = require('body-parser')
const session = require('express-session')
const Database = require('./database')
const http = require('http')
const bcrypt = require('bcryptjs')

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
  Database.getAccount(reqUsername).then((user) => {
    if (user === null) {
      res.status(400).send('{}')
      return
    }
    if (Authenticate(reqPassword, user.password)) {
      res.status(200).send('{}')
    } else {
      res.status(400).send('{}')
    }
  }).catch((err) => {
    console.log(err)
  })
})

function Authenticate (password, dbPassword) {
  return bcrypt.compareSync(password, dbPassword)
}

// status 500 = internal server error.
// status 404 = not found error.

/*
 * Will return an error if no user with the user name or return a JSON string with users credentials.
 */
app.get('/login/org-user/', function (req, res, next) {
  const reqUsername = req.query['username']
  Database.getAccount(reqUsername).then((result) => {
    if (result === null) {
      res.status(400).send('{}')
      return
    }
    res.send(result)
  })
})

http.createServer(app).listen(PORT, function (err) {
  if (err) console.log(err)
  else console.log('HTTP server on http://localhost:%s', PORT)
})
