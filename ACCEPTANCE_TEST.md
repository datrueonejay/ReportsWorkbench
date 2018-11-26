## To test our current features:

!Note: To test any of these features the client and server must be running. Instructions for launching client are included in README.md, and server instructions are located in /server/README.md

### Login
1. Enter username: admin@teq.com
2. Enter password: pass1
3. Click login button
4. Screen should update to show upload data view options

### Upload Data to Database
1. Select Template type "Employment Services" from dropdown.
2. Click add Files button.
3. Add iCare_template file located at "\\app\\src\\test\\resources" in your file explorer.
4. Click "Upload files" button.
5. Window showing parsed JSON should pop up with a dismiss button if server responded. Press the dismiss button.
6. Verify data was uploaded by [logging into Mongodb Atlas](#to-login-to-mongodb-atlas) and checking if the JSON data from the popup is there.

### Org. Last Upload Time
1. After loggin in, select the "Last Uploaded" tab at the top of the window
2. The scene should change to show a table titled "Organization's Last Upload Time"
3. Table should be populated with every row showing a different user, and next to them the date that they uploaded last
4. Can verify that this feature works by following the [steps to Upload Data to Database](#upload-data-to-database) and checking if the last upload time for your user has been updated

### Normalization
1. After logging in, select any Template type from the dropdown in the Upload Data scene
2. Click add Files button
3. Add iCARE_template_bad_formats.xlsx located at \\app\\src\\test\\resources in your file explorer
4. Click "Upload files" button
5. [Login to MongoDB Atlas](#to-login-to-mongodb-atlas) and find latest clients that were uploaded that match those in the excel file. They will be under the collection that you chose in the drop down
6. All the clients with \_id that begin with "bad" should have attribute "valid" = "true" and their Date, Phone Number and Postal Code fields correctly formatted 
   (date format: YYYY-MM-DD, phone number: ###-###-####, Postal Code: A#A#A#)
7. All clients with \_id that begin with "inval" should have attribute "valid" = "false" and the column that is mentioned in their \_id should be listed in their "invalid_columns" attribute.

### Conflict Resolution
1. After loggin in, trigger a conflict by uploading an iCare template with one client twice without changing the Template Type dropdown.
2. Click on the "Conflicts" tab in the top nav bar. 
3. You should see two columns of the client's information for every column provided, with an empty column on the very right for the resolved values.
4. Enter the resolved values (or click "Copy all non-conflicting fields".
5. Click Submit.
6. [Login to MongoDB Atlas](#to-login-to-mongodb-atlas), navigate to the collection that corresponds to the tepmlate type dropdown selection and verify that one instance of the client was inserted into the collection.
7. Repeat steps 1 to 6 but change the mock iCare template to include 2 or more clients. When resolving the conflicts, after clicking on submit for the first client it should automatically update the screen to show the next conflicting client.
8. Repeat steps 1 to 6 and 7 but upload the iCare template 3 or more times. When resolving the conflict, it should display all the 3+ conflicting clients' information.

### Trends Reports
1. After logging in, select the Trends Reports tab in the top nav bar.
2. Select an iCare template from the top left dropdown and a trend type; by age or by month from the top right dropdown.
3. Enter a specific column from that template. It doesn't have to be written exactly as in the template, the application will find the most similar column name to the one you entered. 
4. Click the Generate Trend Report button at the bottom.
5. A popup should appear with the path to where the pdf report was saved to.
6. Open the file and a title with the raw name of the column as well as a bar chart representing the distribution (by whatever you chose in step 2) of those who entered Yes in that column should be within.

### Low Level Reports
1. After logging in, select the Reports tab in the top nav bar.
2. Clicking on any of the three buttons presented should generate a different report pdf and a popup sohuld appear with the location it's location.
3.1 Clicking Language of Service Report will generate a pdf containing titled "Language Report" and a bar graph showing the different languages requested to be translated in the templates, and how many clients chose each. There should also be a pie chart showing the distribution of the chosen languages.
3.2 Clicking Place of Services Report will generate a pdf with the same contents as in step 3.1, however this time it is showing the distribution of the answers for the Where Services Were Recieved column.
3.3 Clicking Population and Child Minding Report will generate a pdf titled "Population and Child Minding Statistics", containing 3 pie charts displaying the age range distribution of clients who received Needs Assessments and Referalls, Employment Related Services, and Language Training services, as well as a bar graph showing how many clients who received each of those services received child minding services as well.

### Custom Reports
1. After Logging in, select the Custom Reports tab in the top nav bar.
2. Choose any iCare template from the left dropdown.
3. Choose any column from the right dropdown.
4. Clicking on "Bar chart report" or "Pie chart report" will generate a pdf and display a popup with the location it was saved in. The pdf should contain a bar chart or pie chart, depending on your selection, showing the different options selected for that column and the distribution of each selection.

#### To login to MongoDB Atlas: 
1. Visit https://www.mongodb.com/cloud
2. Sign in with Username: joseph.sokolon@mail.utoronto.ca, password: thierry25$$
3. Select "Collections" to view the list of collections(including Template Types) on the left and their contents(including Client Records) on the right

