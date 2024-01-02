#!/bin/bash

mongod --fork --logpath /var/log/mongod.log
java -jar /streaming-server-0.0.1.jar > /var/log/notflix.log