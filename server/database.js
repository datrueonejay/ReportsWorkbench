const mysql = require('promise-mysql')

let connection

class Database {
  constructor () {
    mysql.createConnection({
      host: 'cscc01.cxqpmoyut1fr.us-east-2.rds.amazonaws.com',
      user: 'Team25',
      password: '7H8Gh7re6S',
      port: '3306',
      database: 'database1'
    })
      .then((conn) => {
        connection = conn
        console.log('Connected to database.')
      })
      .catch((err) => {
        console.error('Database connection failed: ' + err.stack)
      })
  }

  getUser (username) {
    const query = 'SELECT * FROM `Users` WHERE `username` = ?'
    return connection.query(query, [username])
      .catch((err) => {
        console.log(err)
      })
  }
}

module.exports = new Database()
