#Server

To run the server you will need yarn. Yarn is a package management system for javascript. You can get it here https://yarnpkg.com/lang/en/

In the command line type `yarn install` get all the dependencies the server needs

Then type `yarn run start` to start the server.

I have kept the default port for the server as 8000 however you can change that on your computer by changing the PORT variable at the end of server.js file.

The database files are in the db folder.

The get requests are quite easy. It can be done by using a url similar to the one shown below.

localhost:PORT#/login/get-org-user?username='john doe'

The post requests are a bit more lengthy but can be done by downloading something called postMan. Take a look at the link below for
more in-depth explaination.

https://scotch.io/tutorials/use-expressjs-to-get-url-and-post-parameters
