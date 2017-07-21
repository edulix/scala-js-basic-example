// shadow sbt-scalajs' crossProject and CrossType until Scala.js 1.0.0 is released
import sbtcrossproject.{crossProject, CrossType}

lazy val hello = crossProject(JSPlatform, JVMPlatform, NativePlatform)
  .crossType(CrossType.Full)
  .settings(
    scalaVersion := "2.11.11",
    name := "Scala.js Tutorial",
    libraryDependencies ++= Seq(
      "com.lihaoyi" %%% "utest" % "0.4.4" % "test"
    )
  )
  .jvmSettings()
  .nativeSettings()
  .jsSettings(
    // For now, this is an application with a main method
    scalaJSUseMainModuleInitializer := true,
    jsEnv in Test := {
        val testBackend = sys.props.getOrElse("testBackend", "jsdom")
        val initFiles = Seq((baseDirectory.value / "target" / "scala-2.11" / "scala-js-tutorial-test-jsdeps.js").getCanonicalPath)
        testBackend match {
        case "jsdom" => new JSDOMNodeJSBundlerEnv(initFiles = initFiles)
        case "phantomjs" =>  new PhantomJSBundlerEnv(
          scalaJSPhantomJSClassLoader.value,
          initFiles = initFiles
        )
        case "selenium-chrome" => new SeleniumJSBundlerEnv(
          org.scalajs.jsenv.selenium.Chrome(),
          initFiles = initFiles
        )
        case "selenium-firefox" => new SeleniumJSBundlerEnv(
          org.scalajs.jsenv.selenium.Firefox(),
          initFiles = initFiles
        )
        case x => sys.error(
          s"invalid environment variable 'testBackend' value (='$x'). Allowed values are: jsdom|phantomjs|selenium-chrome|selenium-firefox"
        )
      }
    },
    skip in packageJSDependencies := false,

    jsDependencies ++= Seq(
      RuntimeDOM,
      "org.webjars" % "jquery" % "2.1.4" / "2.1.4/jquery.js"
    ),

    libraryDependencies ++= Seq(
      "org.scala-js" %%% "scalajs-dom" % "0.9.1",
      "be.doeraene" %%% "scalajs-jquery" % "0.9.1"
    ),

    npmDependencies in Compile ++= Seq(
      "source-map-support" -> "0.4.15",
      "jsdom" -> "11.1.0"
    ),

    testFrameworks += new TestFramework("utest.runner.Framework")
    /*,
    webpackConfigFile := Some(baseDirectory.value / "webpack.config.js"),
    webpackConfigFile in Test := Some(baseDirectory.value / "webpack.config.js")*/
  )

lazy val helloJS      = hello.enablePlugins(ScalaJSBundlerPlugin).js
lazy val helloJVM    = hello.jvm
lazy val helloNative = hello.native
