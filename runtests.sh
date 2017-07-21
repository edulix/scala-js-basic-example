#!/bin/bash

# TODO: move this to an sbt command. Not so easy to do
# First command option will be the path to chromedriver
if [ "$1" == "" ]
then
  echo -en "Usage:\n$0 <path/to/chromedriver>\n\nIt will execute tests in all "
  echo -en "available environments. \nReturns 0 only if all tests are successful.\n"
  exit 1
fi

sbt -DtestBackend=jsdom "; clean; helloJS/fastOptJS; helloJS/fastOptJS::webpack; helloJS/test" && \
sbt -DtestBackend=phantomjs helloJS/test && \
sbt -Dwebdriver.chrome.driver=$1 -DtestBackend=selenium-chrome helloJS/test && \
sbt helloJVM/test
