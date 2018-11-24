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
  

    return conflictsCollection.countDocuments({unique_identifier: id, TEMPLATE_NAME: reportTemplateType}).then((count) => {
      console.log("conflictsCollection count is " + count);
      if (count == 1) {
        return conflictsCollection.findOneAndUpdate(
            {unique_identifier: id, TEMPLATE_NAME: reportTemplateType},
            {$push: {conflicts: data}});
      } else {
        templateCollection.countDocuments({_id: id}).then((num) => {
          console.log("templateCollection num is " + num);
          if (num == 1) {
            const otherDuplicate = templateCollection.findOneAndDelete({_id: id}).then((otherDuplicate) =>{
            console.log(otherDuplicate.value._id);
            var conflictRecord = {
                TEMPLATE_NAME : reportTemplateType,
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

  getDatabaseRoot () {
    return this.db
  }
}

module.exports = new Database()
