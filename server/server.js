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
  console.log('HTTP request', req.query['username'], req.method, req.url)
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

/*
 *  Test with the following curl command: curl -H "Content-Type: application/json" http://localhost:8000/org-upload-time/
 */
app.get('/org-upload-time/', function (req, res, next){
   Database.getDatabaseRoot().collection("accounts")
           .find({})
           .sort({follower:-1})
           .toArray(function(err, accounts) {
              if(err) return res.status(500).end(err);

              //var allAccounts = JSON.stringify(accounts);

              var responseJSON = ' {"allTimings" : [';


              for(var i = 0; i < accounts.length; i++)
              {
                 var usernames = accounts[i].username;
                 var lastUpload = accounts[i].lastUploadTime;
                 var singleEntryInArray = '{' + '"orgName":' + '"' + accounts[i].username + '"' + ',' + '"lastUploadTime":' + '"' + lastUpload + '"' + '}';

                 if(i < (accounts.length - 1))
                 {
                     responseJSON = responseJSON + singleEntryInArray + ',';
                 }
                 else
                 {
                     responseJSON = responseJSON + singleEntryInArray;
                 }
              }

              responseJSON  = responseJSON + ']}'

              console.log("The value of the responseJSON is " + responseJSON);

              res.status(200).send(responseJSON);
            });
})


http.createServer(app).listen(PORT, function (err) {
  if (err) console.log(err)
  else console.log('HTTP server on http://localhost:%s', PORT)
})

app.get('/reports/get-report-data/', function (req, res, next) {
  Database.getDatabaseRoot().collection('reports')
    .find({ author: req.reportId })
    .sort({ $natural: 1 })
    .toArray(function (err, report) {
      if (err) return res.status(500).end(err)
      return res.json(report)
    })
})

app.post('/reports/get-data/', function(req, res, next) {
  const reportTemplateType = req.body.template_name;
  // Array of columns we want to get
  const columns = req.body.columns;
  Database.getAllRows(reportTemplateType).then((result) => {
    const data = [];
    // Create an object for each column requestd
    for (const colIndex in columns) {
        const colName = columns[colIndex];
        data.push({ column_name: colName, DataFields: [], Data:[]});
    }

    // Loop through each of the rows from database
    for (const rowIndex in result) {
      // get curr row
      const currRow = result[rowIndex];
      // Loop through each attribute needed, each object in master object
      for (const attributeIndex in data) {
        // Reference to the attribute object
        const currObject = data[attributeIndex];
        // Get name of column
        const colName = currObject.column_name;
        // Get the value of the current row, for the given attribute
        const value = currRow[colName];
        // Handle if value is null, ie column requested did not exist
        if (value == null) {
          return res.status(400).end();
        }
        // Get index of the value in the column names datafields if exists
        index = currObject.DataFields.indexOf(value);
        // Check if value does not exist in datafields for given colName
        if (index == -1) {
          index = currObject.DataFields.push(value) - 1;
          // Add a count for the value of the attribute
          currObject.Data.push(0);
        }
        // Increment the count of that attribute
        currObject.Data[index] += 1;
      }
    }

    return res.json({
      report_name: reportTemplateType,
      data: data
    });
  }).catch((err) => {
    return res.status(500).end(err)
  })
})

app.post('/reports/new-report/', function (req, res, next) {
  const reportTemplateType = Object.keys(req.body)[0]
  const reportData = req.body[reportTemplateType]

  console.log("Recieved upload request from: "+ req.headers['user-id'])

  for (const row in reportData) {
    // Set ID of row
    reportData._id = row
    Database.enterRow(reportTemplateType, row, reportData[row]).catch((err) => {
      return res.status(500).end(err)
    })

    // Database.getDatabaseRoot().collection(reportTemplateType).updateOne({ _id: row }, {$set: reportData[row]}, { upsert: true }, function (err, report) {
    //   if (err) return res.status(500).end(err)
    // })
  }

  // make date string
  var today = new Date();
  var dd = today.getDate();
  var mm = today.getMonth()+1; //January is 0!
  var yyyy = today.getFullYear();
  if(dd<10) {
      dd = '0'+dd
  }
  if(mm<10) {
      mm = '0'+mm
  }
  today = yyyy + '-' + mm + '-' + dd

  // check account is in db
  const reqUsername = req.headers['user-id']
  Database.getAccount(reqUsername).then((user) => {
    if (user === null) {
      res.status(400).send('{}')
      return
    } else {
      // if account exists update their lastUploadTime
      result = Database.getDatabaseRoot().collection("accounts").updateOne(
        {"username" : reqUsername},
        {$set: {"lastUploadTime" : today}}
      );
    }
  }).catch((err) => {
    console.log(err)
  })
  res.status(200).send('{}')
})

//mid-level query TASK B

app.get('/templates/template', function(req, res, next) {
  Database.getDatabaseRoot().collection('TEMPLATES')
  .find(req.get(TEMPLATE_NAME)) //get specific Template
  .toArray(function(err, columns) {
    if(err) return res.status(500).end(err);
    
    columnNames = [];
    var column1 = columns[1]._columns;
    var column2 = columns[2]._mandatory_columns;
    var column3 = columns[3]._column_name_map;
    
    response = {
    		//now i need to combine all 3
    		
    }
     res.status(200).send(response);
  });
})

