# Interruption Tracker

# About

Tired of being interrupted while programming? Then this tool could be for you. Track your interruptions during the day. 

![screenshot.png](docs%2Fassets%2Fscreenshot.png)

Every button click will insert a record in an InfluxDB. 
Later on you can use a visualisation tool like Grafana to create a Dashboard with your interruptions.

# Prerequisites
You'll need to have an Influx v2.x database in your ecosystem where the data is stored to.



# Installation
There are 2 possibilities for installation. 

## 1. Regular installation
* Build the tool using `gradlew.bat bootJar`
* Create a batch script containing: `java -jar <the_name_of_the_jar>.jar`
* Create an application.properties file containing:
```
tracker.influx.url=https://myUrlToMyInfluxDB:443
tracker.influx.token=theInfluxApiToken
tracker.influx.bucket=myBucket
tracker.influx.org=myOrg
```
* Run the batch script

## 2. Docker
Coming soon