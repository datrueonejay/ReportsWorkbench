# Thierry's Minions (Team 25)

Team Members: Joseph, Jayden, Edgar, Rishabh, Balaji


Repository Organization:

/app - Holds all the code used to run the client.

/docs - Holds all the files used to create our team website.

/product_backlog - Holds all our versions of the personas and user stories document.

/server - Holds all the code used to run our server (see README.md in /server).

/sprint_backlog - Holds all the tasks, plans, reports, and burndown charts for each of our sprints.

/code_review - Holds our Deliverable 4-Verification & Code Review doc

/ACCEPTANCE_TEST.md - Holds instructions on how to test each of our implemeted features

****

## How To Build:
1. You need to install maven: https://maven.apache.org/download.cgi. Just unzip it and follow the Readme that comes with it.

2. In terminal/cmd, cd into the ``Team25/app`` directory and run ``mvn compile``.

   * This should finish with ``BUILD SUCCESS``, as should all of the below.

3. If you'd like to package it as a runnable jar that you can double click, run ``mvn jfx:jar``. **YOU MUST HAVE JDK1.8.\* FOR THIS TO WORK** , idk why but I tried this with jdk10, jdk11 and they didn't work. Make sure your JAVA_HOME also points to your jdk1.8.

  * This will place the jar in ``./target/jfx/app/app-1.0-SNAPSHOT-jfx.jar ``

4. If you'd like to compile and launch it right away without making the jar, run ``mvn exec:java``.

5. To unit test our current code first ensure that the server is running. Then cd into Team25/app and run `mvn test`

***

## Demo Video:

[![DEMO VIDEO](http://img.youtube.com/vi/dvqSTOlMGhU/0.jpg)](http://www.youtube.com/watch?v=dvqSTOlMGhU)
