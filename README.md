# AkvelonTestTask

## Pre-requirements

Software used:
  - Apache Ant v 1.9.6
  - Apache Tomcat 7
  - Apache Ivy v 2.4.0
  - Eclipse Mars 4.5.0
  - MySql server v 5.5.44-0ubuntu0.14.04.1

How-to instructions:
  - Go to the root folder, where build.xml is placed
  - Open terminal
  - Type "ant package"
  - Now, go to the "target" folder and copy .war to tomcat's "webapps" directory
  - Setup database
  - Then, launch tomcat (ubuntu: give permissions to "startup.sh" and "catalina.sh", then execute "startup.sh" from tomcat's bin directory)
  - Go to "localhost:8080/AkvelonTestTask/" (port may be different, depends of your settings)

Additional notes:
  - Database connection properies can be found in file src/main/resources/db.properties
  - Script for database table creation: src/main/resources/sql/test_db_person_info.sql
