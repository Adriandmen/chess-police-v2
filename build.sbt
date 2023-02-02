ThisBuild / version      := "1.0.0"
ThisBuild / organization := "com.adrianmensing.twitch"
ThisBuild / scalaVersion := "2.13.10"
assembly / assemblyMergeStrategy := {
  case "module-info.class" | "META-INF/versions/9/module-info.class" => MergeStrategy.discard
  case x =>
    val old = (assembly / assemblyMergeStrategy).value
    old(x)
}

lazy val root = (project in file("."))
  .settings(
    name := "chess-police"
  )

libraryDependencies ++= Seq(
  "com.github.twitch4j"     % "twitch4j"                 % "1.13.0",
  "org.slf4j"               % "slf4j-api"                % "1.7.36",
  "ch.qos.logback"          % "logback-classic"          % "1.4.5"  % Runtime,
  "com.github.pureconfig"  %% "pureconfig"               % "0.17.2",
  "org.scala-lang.modules" %% "scala-parser-combinators" % "2.2.0",
  "org.scalactic"          %% "scalactic"                % "3.2.15" % Test,
  "org.scalatest"          %% "scalatest"                % "3.2.15" % Test
).map(_.excludeAll(ExclusionRule("com.squareup.okio", "okio"), ExclusionRule("com.squareup.okhttp3", "okhttp3")))
