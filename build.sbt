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
    libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.1",
    npmDependencies in Compile += "source-map-support" -> "0.4.15",
    npmDependencies in Compile += "jsdom" -> "11.1.0"
  )

lazy val helloJS = hello.enablePlugins(ScalaJSBundlerPlugin).js
