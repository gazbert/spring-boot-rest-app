#!/bin/bash

#set -x

#
# Bare bones script for starting the app on Linux/OSX systems.
#
# Could be made better, but will do for now...
#
# You need the Java 11 JDK installed.
#
# This script expects all the jar files to live in the lib_dir.
#
# You can change the app_jar var to the version you want to run; it has been defaulted to the current release.
#
# You can start, stop, and query the apps's status: ./app.sh [start|stop|status]
#

# Uncomment if app built with Maven
#lib_dir=./target
# Uncomment if app built with Gradle
lib_dir=./build/libs

# log4j2 config file location
log4j2_config=./config/log4j2.xml

# The sample app's 'fat' jar (Spring Boot app containing all the dependencies)
app_jar=spring-boot-rest-app-0.0.1-SNAPSHOT.jar

# PID file for checking if app is running
pid_file=./.spring-boot-rest-app.pid

# App name
app_name="Spring Boot REST App"

# Process args passed to script
case "$1" in
   'start')
       if [[ -e ${pid_file} ]]; then
          pid=$(cat ${pid_file});
          echo "${app_name} is already running with PID: $pid"
       else
          echo "Starting ${app_name}..."
          java -Xmx64m -Xss256k -Dlog4j.configurationFile=file:${log4j2_config} --illegal-access=deny -jar ${lib_dir}/${app_jar} 2>&1 >/dev/null &

          echo "${app_name} started with PID: $!"
          echo $! > ${pid_file}
       fi
       ;;

    'stop')
       if [[ -e ${pid_file} ]]; then
          pid=$(cat ${pid_file});
       else
          echo "${app_name} is not running. Nothing to stop."
          exit
       fi
       echo "Stopping ${app_name} instance running with PID: $pid ..."
       kill ${pid}
       sleep 1
       pid=`ps -aef | grep ${pid} | grep -v grep`
       if [[ ${pid} -gt 1 ]]; then
          echo "Failed to stop ${app_name}. Manual kill required!"
       else
          echo "${app_name} has stopped."
          rm ${pid_file}
       fi
       ;;

   'status')
      if [[ -e ${pid_file} ]]; then
         pid=$(cat ${pid_file});
         echo "${app_name} is running with PID: $pid"
      else
         echo "${app_name} is not running."
      fi
      ;;

   *)
         echo "Invalid args. Usage: $0 [start|stop|status]"
      ;;
esac
