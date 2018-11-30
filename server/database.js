const MongoClient = require('mongodb').MongoClient

const url = 'mongodb+srv://Team25:7H8Gh7re6S@team25-dsrl7.mongodb.net'

class Database {
  constructor () {
    MongoClient.connect(url, { useNewUrlParser: true }, (err, client) => {
      if (err) {
        console.error('Error connecting to MongoDB' + err)
        return
      }
      console.log('Connected to MongoDB!')
      this.db = client.db('main')
    })
  }

  getAccount (username) {
    const collection = this.db.collection('accounts')
    return collection.findOne({ 'username': username })
      .catch(err => {
        console.error(err)
      })
  }

  getAllAccounts()
  {
    var allAccounts;
    // db.artists.find( { albums: { $exists: false }} )
    this.db.collection("accounts").find({}).sort({follower:-1}).toArray(function(err, accounts) {
        if (err) return res.status(500).end(err);

        return accounts;
    });
  }

  getDataBasedOn2Constraints(reportTemplateType, constraint1)
  {
    console.log("The value of the reportTemplateType is" + reportTemplateType);

    return this.db.collection(reportTemplateType).find().toArray();
  }

  /**
   * check if there's already a conflict in the CONFLICTS collection with that id and templateType
   *   if yes: add data to that document's conflicts array
   * if not :
   *   check if that template collection has an existing doc with _id == id
   *     if yes: make a new document in CONFLICTS with the new data and the conflict, delete the conflict doc from the template collection
   *   if not: there's no conflicting document, add it to the template collection
   *
   *
  */
  enterRow(reportTemplateType, id, data) {
    const templateCollection = this.db.collection(reportTemplateType);
    const conflictsCollection = this.db.collection("CONFLICTS");


    return conflictsCollection.countDocuments({unique_identifier: id, template_name: reportTemplateType}).then((count) => {
      console.log("conflictsCollection count is " + count);
      if (count == 1) {
        return conflictsCollection.findOneAndUpdate(
            {unique_identifier: id, template_name: reportTemplateType},
            {$push: {conflicts: data}});
      } else {
        templateCollection.findOne({_id: id}).then((conflict) => {
          if (conflict != null) {
            // First check if they are exactly the same document
            if (!checkIfSameDocument(conflict, data)) {
              const otherDuplicate = templateCollection.findOneAndDelete({_id: id}).then((otherDuplicate) =>{
              var conflictRecord = {
                  template_name : reportTemplateType,
                  unique_identifier : id,
                  conflicts : [
                    data,
                    otherDuplicate.value
                  ]
              }
              console.log("new conflict detected, " + conflictRecord.conflicts[0]._id + " will be inserted into CONFLICTS.");
              return conflictsCollection.insertOne(conflictRecord);
              })
            } else {
              console.log("Document is identical to already existing document. No action taken.");
            }
          } else {
            console.log("no conflicts, " + data._id + " will be inserted.");
            return templateCollection.insertOne(data);
          }
        })
      }
    })
  }

  getAllRows(reportTemplateType) {
    // return collection of all documents for given template type
    console.log("The value of reportTemplateType is: "  + reportTemplateType);
    return this.db.collection(reportTemplateType).find().toArray();
  }

  deleteRow(collectionName, key, parameter) {
    const collection = this.db.collection(collectionName);
    console.log(key, parameter);
    return collection.deleteOne({ "unique_identifier" : parameter})
  }

  getDatabaseRoot () {
    return this.db
  }
}

function checkIfSameDocument(document1, document2) {
    for (const key in document1){
      if (!(document1[key] === document2[key])){
        console.log("Conflict detected", key, " has conflicting data.");
        return false
      }
    }
    for (const key in document2){
      if (!(document1[key] === document2[key])){
        console.log("Conflict detected", key, " has conflicting data.");
        return false
      }
    }
    return true
}

module.exports = new Database()
