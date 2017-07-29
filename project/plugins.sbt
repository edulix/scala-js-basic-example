
addSbtPlugin("org.scala-js"      % "sbt-scalajs"                % "0.6.19")
addSbtPlugin("org.scoverage"     % "sbt-scoverage"              % "1.3.5")
addSbtPlugin("org.scala-native"  % "sbt-crossproject"           % "0.2.0")
addSbtPlugin("org.scala-native"  % "sbt-scalajs-crossproject"   % "0.2.0")
addSbtPlugin("ch.epfl.scala"     % "sbt-scalajs-bundler"        % "0.7.0")
addSbtPlugin("org.scala-native"  % "sbt-scala-native"           % "0.3.1")
addSbtPlugin("com.lucidchart"    % "sbt-scalafmt"               % "1.9")
addSbtPlugin("de.heikoseeberger" % "sbt-header"                 % "2.0.0")

libraryDependencies += "org.scala-js" %% "scalajs-env-selenium" % "0.1.3"
