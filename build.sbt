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
    jsEnv := {
        val testBackend = sys.props.getOrElse("testBackend", "phantomjs")
        sLog.value.info(s"jsSettings: testBackend = $testBackend")
        testBackend match {
        case "jsdom" => {
          new org.scalajs.jsenv.jsdomnodejs.JSDOMNodeJSEnv(
          org.scalajs.jsenv.jsdomnodejs.JSDOMNodeJSEnv.Config()
            .withArgs(
              List(
                (
                  baseDirectory.value / "target" / "scala-2.11" /
                  "scalajs-bundler" / "main" / "scala-js-tutorial-fastopt-bundle.js"
                ).getAbsolutePath
              )
            )
          )
        }
        /* includes phantomjs, the default */
        case "phantomjs" =>  Def.settingDyn {
          PhantomJSEnv(
            "phantomjs",
            Seq(
              (
                baseDirectory.value / "target" / "scala-2.11" / "scalajs-bundler" /
                "main" / "scala-js-tutorial-fastopt-bundle.js"
              ).getAbsolutePath
            )
          )
        }.value
        case x => sys.error(
          s"invalid environment variable 'testBackend' value (='$x'). Allowed values are: jsdom|phantomjs"
        )
      }
    },
    libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.1",
    npmDependencies in Compile += "source-map-support" -> "0.4.15",
    npmDependencies in Compile += "jsdom" -> "11.1.0",
    webpackConfigFile := Some(baseDirectory.value / "webpack.config.js")
  )

lazy val helloJS = hello.enablePlugins(ScalaJSBundlerPlugin).js
