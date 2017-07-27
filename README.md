[![Build Status](https://travis-ci.org/nVotesOrg/scala-js-basic-example.svg?branch=master)](https://travis-ci.org/nVotesOrg/scala-js-basic-example) [![Gitter](https://img.shields.io/badge/gitter-join%20chat-green.svg)](https://gitter.im/nVotesOrg/scala-js-basic-example) [![Codecov](https://codecov.io/github/nVotesOrg/scala-js-basic-example.svg/coverage.svg?branch=master)](https://codecov.io/github/nVotesOrg/scala-js-basic-example.svg?branch=master)

# Scalajs basic tutorial

This is based on the scala.js basic tutorial modified to be a skeleton to be used as
a skeleton for projects. But it includes additionally:
- Cross-compiling for three platforms: ScalaNative, Scala.js and ScalaJVM
- Example shared code between platforms and some platform-specific code
- Unit testing with a wide range of backends
- Integration with Travis CI to execute the unit tests
- Integration with coverage for tests
- A github condition that makes unit test obligatory to pass before merging into master [TODO]
- github condition that makes obligatory that code styling conforms to the specification [TODO]
- Symbol exports in Scala.js [TODO]
- Tests for Scala Native [TODO]

## How to run in the web browser

Dependencies:
- sbt >= 0.13
- If using Scala Native, install node
- If using Scala JVM, install Scala JVM dependencies (mainly Java 8)
- If using Scala JS, Node.js >= 6

Then compile to test in the web browser:

    sbt helloJS/fastOptJS helloJS/fastOptJS::webpack

And open in the web browser the `index.html` file.

## How to run tests

You can run the tests in many ways.

### With Scala JVM

Execute:

    sbt helloJVM/test

### With Scala.js and jsdom

Execute:

    sbt -DtestBackend=jsdom helloJS/test

### With Scala.js and phantomjs

Install the `phantomjs` command in your system, then run:

    sbt -DtestBackend=phantomjs helloJS/test

### With Scala.js and Selenium with Google Chrome

Install `selenium`, `xvfb` and downlod selenium-chromedriver, then run the
following commands, but changing /path/to/chromedriver to the correct path:

    # These two prevent selenium opening chrome windows in your face
    nohup Xvfb :1 &
    export DISPLAY=:1

    sbt -Dwebdriver.chrome.driver=/path/to/chromedriver -DtestBackend=selenium-chrome helloJS/test

## Execute tests in all environments

Install all the dependencies above and then execute:

    ./runtests.sh /path/to/chromedriver
