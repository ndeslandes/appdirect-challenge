# Appdirect-challenge

Features implemented :
* Single Sign On with the AppDirect OpenID provider
* Subscription Notification Endpoint (create, change, cancel, status)
* User Assignment Endpoint (assign, unassign)

Application is deploy on heroku on https://boiling-shore-27676.herokuapp.com/

## Installation
mvn clean package
the war will be in target/appdirect-challenge-1.war
 the war is also executable
java -jar target/appdirect-challenge-1.war

The zip Product-77081.zip is an export of the product settings from the market jacefoil-test.byappdirect.com

##Frameworks and Libraries
### Spring Boot: http://projects.spring.io/spring-boot/
Spring framework for micro-service
####Spring Boot JDBC
JDBC over JPA for better control of database query
####Spring Boot Security
Spring security implementation
####Spring Boot Test
Test utilities for Spring Boot application
###Spring Security: http://projects.spring.io/spring-security/
####Spring Security OAuth
Spring implementation of OAuth 1
#####Spring Security OpenID
Spring implementation of OpenID
###WebJars: http://www.webjars.org/
Use maven for client-side dependencies
###Semantic UI: http://semantic-ui.com/
Easy UI framework
###jQuery: https://jquery.com/
Javascript utility library
###PostgreSQL: http://www.postgresql.org/
Default database of Heroku
###HyperSQL: http://hsqldb.org/
In-memory database for test purpose

## Usage

###Subscription Create Notification URL
https://boiling-shore-27676.herokuapp.com/api/notification/subscription/create?url={eventUrl}

###Subscription Change Notification URL
https://boiling-shore-27676.herokuapp.com/api/notification/subscription/change?url={eventUrl}

###Subscription Cancel Notification URL
https://boiling-shore-27676.herokuapp.com/api/notification/subscription/cancel?url={eventUrl}

###Subscription Status Notification URL
https://boiling-shore-27676.herokuapp.com/api/notification/subscription/status?url={eventUrl}

###User Assignment Notification URL
https://boiling-shore-27676.herokuapp.com/api/notification/access/assign?url={eventUrl}

###User Unassignment Notification URL
https://boiling-shore-27676.herokuapp.com/api/notification/access/unassign?url={eventUrl}

###Authentication Login URL
https://boiling-shore-27676.herokuapp.com/login/openid?openid_identifier={openid}

###Authentication Logout URL
https://boiling-shore-27676.herokuapp.com/logout