# Appdirect-challenge

Features implemented :
* Single Sign On with the AppDirect OpenID provider
* Subscription Notification Endpoint (create, change, cancel, status)

## Installation

mvn compile war:war
the war will be in target/appdirect-challenge-1.war

Alternate installation
mvn package
java -jar target/appdirect-challenge-1.jar

## Usage

###Subscription Create Notification URL
https://boiling-shore-27676.herokuapp.com/api/notification/subscription/create?url={eventUrl}

###Subscription Change Notification URL
https://boiling-shore-27676.herokuapp.com/api/notification/subscription/change?url={eventUrl}

###Subscription Cancel Notification URL
https://boiling-shore-27676.herokuapp.com/api/notification/subscription/cancel?url={eventUrl}

###Subscription Status Notification URL
https://boiling-shore-27676.herokuapp.com/api/notification/subscription/status?url={eventUrl}

###Authentication Login URL
https://boiling-shore-27676.herokuapp.com/login/openid?openid_identifier={openid}

##User Assignment Notification URL

##User Unassignment Notification URL