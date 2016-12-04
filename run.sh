#!/bin/bash

# Place your compile and execute script here.
# You can write any bash script that will run on campus linux machines.
# The below script will compile and execute the HelloEC program.
# HelloEC will also be passed the first argument to this script, a config file.

configFile=$1

echo "Building gpac"
cd gpac
mvn clean install -DskipTests
cd ..
java -jar gpac/target/gpac-1.0-SNAPSHOT.jar $configFile
