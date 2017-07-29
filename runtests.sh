#!/bin/bash

# TODO: move this to an sbt command. Not so easy to do
# First command option will be the path to chromedriver
if [ "$2" == "" ]
then
  echo -en "Usage:\n$0 <path/to/chromedriver> </path/to/re2/prefix>\n\nIt will execute tests in all "
  echo -en "available environments. \nReturns 0 only if all tests are successful.\n"
  exit 1
fi

CHROMEDRIVER_PATH=$1
RE2_PREFIX_PATH=$2
export LD_LIBRARY_PATH=$RE2_PREFIX_PATH/lib/

# Execute helloNative tests
sbt -DnativeLinkingOptions="-L$RE2_PREFIX_PATH/lib/" -DnativeCompileOptions="-I$RE2_PREFIX_PATH/include/" "; clean; helloNative/test" && \
# Execute JVM tests, creating also a coverage report
sbt "; coverage; helloJVM/test; coverageReport; coverageOff" && \
# Execute helloJS tests with jsdom
sbt -DtestBackend=jsdom "; helloJS/fastOptJS; helloJS/fastOptJS::webpack; helloJS/test" && \
# Execute helloJS tests with phantomjs
sbt -DtestBackend=phantomjs helloJS/test && \
# Execute helloJS tests with selenium-chrome
sbt -Dwebdriver.chrome.driver=$CHROMEDRIVER_PATH -DtestBackend=selenium-chrome helloJS/test
