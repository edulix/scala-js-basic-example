[![Build Status](https://travis-ci.org/nVotesOrg/scalaSkeleton.svg?branch=master)](https://travis-ci.org/nVotesOrg/scalaSkeleton) [![Gitter](https://img.shields.io/badge/gitter-join%20chat-green.svg)](https://gitter.im/nVotesOrg/scalaSkeleton) [![codecov](https://codecov.io/gh/nVotesOrg/scalaSkeleton/branch/master/graph/badge.svg)](https://codecov.io/gh/nVotesOrg/scalaSkeleton) [![License](https://img.shields.io/badge/license-Apache%202-blue.svg)](https://raw.githubusercontent.com/47deg/github4s/master/LICENSE)

# Introduction

This is based on the scala.js basic tutorial modified to be a skeleton to be used as
a skeleton for projects. But it includes additionally:
- Cross-compiling for three platforms: ScalaNative, Scala.js and ScalaJVM
- Example shared code between platforms and some platform-specific code
- Unit testing with a wide range of backends
- Integration with Travis CI to execute the unit tests
- Uses Travis CI containers and Travis CI cache for faster builds
- A gitter chat
- Github is configured to protect master branch requiring a pull request with a
  valid Travis CI build before merging into master
- Integration with codecov for JVM unit tests
- Generates code documentation automatically [TODO]
- Integration with codecov for Scala JS and maybe Scala Native unit tests [TODO]
- Symbol exports in Scala.js [TODO]

# Execution

# In the web browser

Dependencies:
- sbt >= 0.13
- If using Scala Native, install LLVM/Clang 3.7 or newer, libunwind 0.99 or newer, Re2 2017-01-01 (see http://www.scala-native.org/en/latest/user/setup.html for details)
- If using Scala JVM, install Scala JVM dependencies (mainly Java 8)
- If using Scala JS, Node.js >= 6

Then compile to test in the web browser:

    sbt helloJS/fastOptJS helloJS/fastOptJS::webpack

And open in the web browser the `index.html` file.

# Tests

You can run the tests in many ways.

## Run tests with Scala JVM

Execute:

    sbt helloJVM/test

## Run tests with Scala Native

Execute:

    sbt helloNative/test

Note: scala-native seems to require google re2 library. If it's installed in a
non standard prefix, execute it this way:

    export RE2_PREFIX_PATH=/path/to/prefix # `/usr` or `/usr/local` are standard values
    export LD_LIBRARY_PATH=$RE2_PREFIX_PATH/lib/
    sbt -DnativeLinkingOptions="-L$RE2_PREFIX_PATH/lib/" -DnativeCompileOptions="-I$RE2_PREFIX_PATH/include/" "; clean; helloNative/test"

## Run tests with Scala.js and jsdom

Execute:

    sbt -DtestBackend=jsdom helloJS/test

## Run tests with Scala.js and phantomjs

Install the `phantomjs` command in your system, then run:

    sbt -DtestBackend=phantomjs helloJS/test

## Run tests with Scala.js and Selenium with Google Chrome

Install `selenium`, `xvfb` and downlod selenium-chromedriver, then run the
following commands, but changing /path/to/chromedriver to the correct path:

    # These two prevent selenium opening chrome windows in your face
    nohup Xvfb :1 &
    export DISPLAY=:1

    sbt -Dwebdriver.chrome.driver=/path/to/chromedriver -DtestBackend=selenium-chrome helloJS/test

## Run tests in all environments

Install all the dependencies above and then execute:

    ./runtests.sh /path/to/chromedriver /path/to/re2/prefix

NOTE: `/usr` or `/usr/local` are standard values for `/path/to/re2/prefix`

# Continuous Integration with Travis CI

This project uses Travis CI for continuous integration. Travis CI is configured
to check all the following items execute successfully:
- Execute unit tests in all available environments
- Check that copyright notice headers are in place
- Check that code formatting conforms to specification with scalafmt

# Formatting

This project uses scalafmt to format its source code. It's mandatory to conform
to the specified format.

To check the correct formatting of the source code, execute:

    sbt sbt "; scalafmt::test; sbt:scalafmt::test; test:scalafmt::test;"

To format the source code, execute:

    sbt "; scalafmt; sbt:scalafmt; test:scalafmt;"

## Copyright notice headers


It's mandatory that all source files have a copyright notice header, which is
checked using the sbt-header plugin.

To check all files have the appropiate copyright notice header, execute:

    sbt headerCheck

NOTE: It will show an exception saying "Unable to auto detect project license".
You can ignore it, it did actually auto detect the project license.

To add to all files the appropiate copyright notice header, execute:

    sbt headerCreate

# Checking dependencies updates

It's good to know if there's any library dependency that can be updated. Thanks
to our use of `sbt-updates`, you can do that executing:

    sbt dependencyUpdates
