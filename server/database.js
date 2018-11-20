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

  enterRow(reportTemplateType, id, data) {
    const templateCollection = this.db.collection(reportTemplateType);
    return templateCollection.insertOne(data)
    .catch(err => {

      const otherDuplicate = templateCollection.findOneAndDelete({ _id: id}); 
        // move the old duplicate client from 
        // the templateTpye collection to the CONFLICTS collection
      var conflictRecord = {
        TEMPLATE_NAME : reportTemplateType,
        uniqueIdentifier : id,
        conflicts : [
          data,
          otherDuplicate
        ]
      }
      const conflictsCollection = this.db.collection("CONFLICTS");
      conflictsCollection.updateOne({ uniqueIdentifier: id }, { $set: conflictRecord}, {upsert: true});
        // if uniqueIdentifier exists in conflictsCollection then add data to its conflicts array***
        // *** ABOVE IMPLEMENTATION DOESN'T WORK
    });
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
