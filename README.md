# Thierry's Minions (Team 25)

Team Members: Joseph, Jayden, Edgar, Rishabh, Balaji
You can visit our teams website here https://cscc01.github.io/Team25/ to find more information about our team.


Repository Organization:

/docs - Holds all the files used to create our team website.

****

## How To Build:
1. You need to install maven: https://maven.apache.org/download.cgi. Just unzip it and follow the Readme that comes with it.

2. In terminal/cmd, cd into the ``Team25/app`` directory and run ``mvn compile``.

   * This should finish with ``build complete``, as should all of the below.
   
3. If you'd like to package it as a runnable jar that you can double click, run ``mvn jfx:jar``. 

  * This will place the jar in ``./target/jfx/app/app-1.0-SNAPSHOT-jfx.jar ``

4. If you'd like to compile and launch it right away withouth making the jar, run ``mvn exec:java``.
