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
    this.deleteRow(reportTemplateType, "_id", id)
    return templateCollection.updateOne({ _id: id }, {$setOnInsert:data}, { upsert: true })
  }

  deleteRow(collectionName, key, parameter) {
    const collection = this.db.collection(collectionName);
    console.log(key, parameter);
    return collection.deleteOne({ key : parameter})
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
