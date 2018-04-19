#!/bin/sh

mvn tomcat7:redeploy && echo "see \033[4mhttp://localhost:8080/tomcatSqlite\033[0m"
