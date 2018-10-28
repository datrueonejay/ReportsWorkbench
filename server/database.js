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

  getDatabaseRoot () {
    return this.db
  }
}

module.exports = new Database()
