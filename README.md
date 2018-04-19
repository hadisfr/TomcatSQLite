Tomcat SQLite
===
a simple example of using SQLite DataBase Management System (DBMS) with Apache Tomcat

This project uses Apache Maven 2 to manage dependencies, [xerial's SQLite JDBC Driver](https://github.com/xerial/sqlite-jdbc) to communicate with database, and SQLite as DBMS to run a servlet on Apache Tomcat.

It's possible to make a new tomcat project using maven with running `create.sh`, or:

```
mvn archetype:generate -D groupId=ir.hadisafari.tomcatSqlite -D artifactId=tomcatSqlite -D archetypeArtifactId=maven-archetype-webapp -D interactiveMode=false
```

To run project, you can run `deploy.sh`, or simply use `mvn tomcat7:redeploy` after installation of sqlite. After that, the result may be available on [`http://localhost:8080/tomcatSqlite/`](http://localhost:8080/tomcatSqlite/) based on the configurations of your tomcat.

Also, you may need to add these lines to your `$CATALINA_HOME/conf/tomcat-users.xml`:
```
<role rolename="manager-script"/>
<user username="admin" password="" roles="manager-script"/>
```

The database file (`sample.db`) will be create in `$CATALINA_HOME/webapps/tomcatSqlite/WEB-INF/sample.db` after the first run. You may want to use graphical apps like [phpLiteAdmin](https://www.phpliteadmin.org/) to read or edit it.

It's recommended to read [Using SQLiteJDBC with Maven2](https://github.com/xerial/sqlite-jdbc#using-sqlitejdbc-with-maven2) in documentations of xerial's SQLite JDBC Driver.
