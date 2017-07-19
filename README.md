# Scalajs basic tutorial

This is based on the scala.js basic tutorial modified to be a skeleton to be used as
a skeleton for projects. But it includes additionally:
- Cross-compiling for three platforms: ScalaNative, Scala.js and ScalaJVM [TODO]
- Example shared code between platforms and some platform-specific code
- Unit testing with a wide range of backends [IN-PROGRESS, missing firefox & cross-platform]
- Integration with Travis CI to execute the unit tests [TODO]
- A pre-commit hook that makes unit test obligatory to pass locally [TODO]
- Symbol exports in Scala.js [TODO]

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

### With Scala.js and jsdom

Execute:

    sbt -DtestBackend=jsdom helloJS/test

### With Scala.js and phantomjs

Install the `phantomjs` command in your system, then run:

    sbt -DtestBackend=phantomjs helloJS/test

### With Scala.js and Selenium with Google Chrome

TODO: Make it headless

Install `selenium`, `xvfb` and downlod selenium-chromedriver, then run the
following commands, but changing /path/to/chromedriver to the correct path:

    # These two prevent selenium opening chrome windows in your face
    nohup Xvfb :1 &
    export DISPLAY=:1

    sbt -Dwebdriver.chrome.driver=/path/to/chromedriver -DtestBackend=selenium-chrome helloJS/test
