// shadow sbt-scalajs' crossProject and CrossType until Scala.js 1.0.0 is released
import sbtcrossproject.{crossProject, CrossType}

lazy val hello = crossProject(JSPlatform)
  .crossType(CrossType.Full)
  .settings(
    scalaVersion := "2.11.11",
    name := "Scala.js Tutorial"
  )
  .jsSettings(
    // For now, this is an application with a main method
    scalaJSUseMainModuleInitializer := true,
    jsDependencies += RuntimeDOM,
    jsEnv in Test := {
        val testBackend = sys.props.getOrElse("testBackend", "jsdom")
        testBackend match {
        case "jsdom" => new JSDOMNodeJSBundlerEnv()
        case "phantomjs" =>  new PhantomJSBundlerEnv(scalaJSPhantomJSClassLoader.value)
        case "selenium-chrome" => new SeleniumJSBundlerEnv(org.scalajs.jsenv.selenium.Chrome())
        case "selenium-firefox" => new SeleniumJSBundlerEnv(org.scalajs.jsenv.selenium.Firefox())
        case x => sys.error(
          s"invalid environment variable 'testBackend' value (='$x'). Allowed values are: jsdom|phantomjs|selenium-chrome|selenium-firefox"
        )
      }
    },
    libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.1",
    npmDependencies in Compile += "source-map-support" -> "0.4.15",
    npmDependencies in Compile += "jsdom" -> "11.1.0"/*,
    webpackConfigFile := Some(baseDirectory.value / "webpack.config.js"),
    webpackConfigFile in Test := Some(baseDirectory.value / "webpack.config.js")*/
  )

lazy val helloJS = hello.enablePlugins(ScalaJSBundlerPlugin).js
