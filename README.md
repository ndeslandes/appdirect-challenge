# Appdirect-challenge

Features implemented :
* Single Sign On with the AppDirect OpenID provider
* Subscription Notification Endpoint (create, change, cancel, status)
* User Assignment Endpoint (assign, unassign)

## Installation
mvn compile war:war
the war will be in target/appdirect-challenge-1.war

Alternate installation
mvn package
java -jar target/appdirect-challenge-1.jar

##Frameworks and Libraries
* Spring Boot: http://projects.spring.io/spring-boot/
  * Spring Boot JDBC
  * Spring Boot Security
  * Spring Boot Test
* Spring Security: http://projects.spring.io/spring-security/
  * Spring Security OAuth
  * Spring Security OpenID
* WebJars: http://www.webjars.org/
* Semantic UI: http://semantic-ui.com/
* jQuery: https://jquery.com/
* PostgreSQL: http://www.postgresql.org/
* HyperSQL: http://hsqldb.org/

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