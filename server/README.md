# Server

To run the server you will need yarn. Yarn is a package management system for javascript. You can get it here https://yarnpkg.com/lang/en/

In the command line type `yarn install` get all the dependencies the server needs

Then type `yarn start` to start the server.

To keep the code consistent I added a linter which will correct spacing and other small errors and point out issues.

To use the linter type `yarn fix`, it will fix everything it can and direct you to any errors it can't fix.

I have kept the default port for the server as 8000 however you can change that on your computer by changing the PORT variable at the end of server.js file.

To get information about an account we can just put:
http://localhost:8000/login/org-user?username=value
In the browser to find information about the account.
(Eventually we will remove this as it posts password aswell, we just have it now for debugging purposes.)

The login request works as follows:
1. We need to send a post request to http://localhost:8000/login/org-user
2. We need to add `Content-Type: application/json` as a header for the request
3. We need to add to the body the json object in form `{"username": "value", "password": "value"}`

To make this request in command line you can enter:
`curl -H "Content-Type: application/json" -d '{"username":"user", "password":"pass"}' http://localhost:8000/login/org-user`

This request can also be made by setting the same parameters using postman.

https://scotch.io/tutorials/use-expressjs-to-get-url-and-post-parameters
