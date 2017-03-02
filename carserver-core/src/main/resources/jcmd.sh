#!/bin/bash

JARNAME="car-server-core-1.0-SNAPSHOT.jar"
java -jar   -Dlog4j.configurationFile="configs/log4j2.xml" -Dlog4j2.disable.jmx=true ${JARNAME}
