# Interruption Tracker

It takes up to 20 minutes to re-focus again after you're interrupted.

You're a developer and you're getting interrupted all the time? Use Interruption Tracker to 
track your interruptions during the day. Make it visible - Change it. 

# About

This tool can track your interruptions during the day. It's anonymous. 
Every user will get a unique ID which will be stored as a browser cookie. As long as you use the same browser
the ID stays the same. 

![screenshot.png](docs%2Fassets%2Fscreenshot.png)

Every button click will insert a record in an InfluxDB (v2.x). 
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

The Docker Image is
`ghcr.io/waschndolos/interruption-tracker:latest`

### e.g. use docker-compose
```
version: '3.8'

services:
  interruption-tracker:
    image: ghcr.io/waschndolos/interruption-tracker:latest
    container_name: interruption-tracker
    ports:
      - "9999:9999" # host:container - you're welcome :) 
    environment:
      - TRACKER_INFLUX_URL=<your influx url>
      - TRACKER_INFLUX_TOKEN=<your rw token for the bucket>
      - TRACKER_INFLUX_BUCKET=<your bucket name>
      - TRACKER_INFLUX_ORG=<your org name>
    restart: always  # Ensure the container restarts if it crashes or the host reboots

```

