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
  console.log("The value of user is:" + req.body);
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

app.post('/trends/all-data/', function (req, res, next) 
{
    var reportTemplateType = req.body.template_name;
    
    console.log("The type of the report template is: " + reportTemplateType);

    var contraint1Validity = "567";

    Database.getDataBasedOn2Constraints(reportTemplateType, contraint1Validity).then((result) => {
    
   
    /*for(var i = 0; i < result.length; i++)
    {
      console.log("The value of if this user is valid: " + result[i].valid);
    }*/

    var jsonString = JSON.stringify(result);

    console.log("The value of the data is " + jsonString);
    // Create an object for each column requestd
    /*for (const colIndex in columns) {
        const colName = columns[colIndex];
        console.log("The value of the colName is " + colName);
        data.push({ column_name: colName, DataFields: [], Data:[]});
    }

    // Loop through each of the rows from database
    for (const rowIndex in result) {
      // get curr row
      const currRow = result[rowIndex];
      console.log("The value of the current row index is " + rowIndex);
      // Loop through each attribute needed, each object in master object
      for (const attributeIndex in data) {
        // Reference to the attribute object
        const currObject = data[attributeIndex];
        // Get name of column
        const colName = currObject.column_name;
        // Get the value of the current row, for the given attribute
        const value = currRow[colName];
        console.log("The value of the data is: " + value);
        // Handle if value is null, ie column requested did not exist
        if (value == null) {//
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
    }*/

    console.log("The value of the data at the end is" + result);

    return res.json({
      report_name: reportTemplateType,
      data: jsonString
    });
  
  }).catch((err) => {
    return res.status(500).end(err)
  })
})


//TaskC conflict feature
app.get('/conflict', function(req, res, next) {
	const dc = Database.getDatabaseRoot().collection('CONFLICTS')
	//can also use findOne() but need to make sure it works
	.find()
	.toArray(function (err, conflicts) {
		if(err) return res.status(500).end(err);

		//get the first conflict
		response = conflicts[0]
    console.log(response);
		res.status(200).send(response);
	})
})

app.post('/reports/get-data/', function(req, res, next) {
  console.log(req.body);
  const reportTemplateType = req.body.template_name;
  console.log("The value of the template_name is " + reportTemplateType);
  // Array of columns we want to get
  const columns = req.body.columns;

  console.log("The values for all columns " + columns);
  Database.getAllRows(reportTemplateType).then((result) => {
    const data = [];

    // Create an object for each column requestd
    for (const colIndex in columns) {
        const colName = columns[colIndex];
        console.log("The value of the colName is " + colName);
        data.push({ column_name: colName, DataFields: [], Data:[]});
    }

    // Loop through each of the rows from database
    for (const rowIndex in result) {
      // get curr row
      const currRow = result[rowIndex];
      console.log("The value of the current row index is " + rowIndex);
      // Loop through each attribute needed, each object in master object
      for (const attributeIndex in data) {
        // Reference to the attribute object
        const currObject = data[attributeIndex];
        // Get name of column
        const colName = currObject.column_name;
        // Get the value of the current row, for the given attribute
        const value = currRow[colName];
        console.log("The value of the data is: " + value);
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

    console.log("The value of the data at the end is");
    console.log(data);

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
  const reportData = req.body[reportTemplateType] //reportData is map of {client1->data, client2->data} etc...

  console.log("Recieved upload request from: "+ req.headers['user-id'])

  for (const row in reportData) { //row represents one client object's key
    var rowData = reportData[row]; //holds one client's object's value
    rowData._id = row; // set client object's value's id key to client object's key
    Database.enterRow(reportTemplateType, row, rowData).catch((err) => {
      console.log("error in server.js");
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

//mid-level query TASK C

app.get('/templates/template', function(req, res, next) {
  const TEMPLATE_NAME = req.headers['template_name']

  const thing = Database.getDatabaseRoot().collection('TEMPLATES')
  .find({_id: TEMPLATE_NAME}) //get specific Template
  .toArray(function(err, columns) {
    if(err) return res.status(500).end(err);

    //delete id
    delete columns[0]._id

    response = columns[0]
    res.status(200).send(response);
  });
})


// mid-level query taskB:
// Get Endpoint to return names of all templates

app.get('/templates/all-templates/', function(req, res, next) {
  Database.getDatabaseRoot().collection('TEMPLATES')
  .find() //get all the documents in the collection
  .sort({"_id": 1}) //sort them by id in ascending order
  .toArray(function(err, templates) {
    if(err) return res.status(500).end(err);

    templateNames = [];

    for (var i = 0; i < templates.length; i++)
    {
      var templateName = templates[i]._id;
      templateNames.push(templateName);
    }

    response = {
      Template_Names : templateNames
    }

    res.status(200).send(templateNames);
  });
})

//Insert new row in corresponding template collection. Delete conflict object in conflict table  (Joey) /conflict
app.post('/conflict', function(req, res) {
   // Insert new row in corresponding template collection.
   console.log(req.body);
   const template_name = req.body.template_name
   const resolution = req.body.resolution
   const id = req.body.unique_identifier
   const conflict_id = req.body.conflict_id
   Database.deleteRow("CONFLICTS", '_id', id).then(()=>{
     Database.enterRow(template_name, id, resolution).then(()=>{
       res.status(200).send('{}')
       })
   }).catch((err)=>{
     console.log(err);
   })
   // Delete conflict object in conflicts table
})
