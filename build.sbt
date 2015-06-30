name := "commons-sandbox"

version := "0.1.0-alpha"

organization  := "inet-oxford"

scalaVersion := "2.11.6"

autoAPIMappings := true

libraryDependencies ++= Seq(
  "io.reactivex" % "rxscala_2.11" % "0.25.0",
  "com.typesafe.akka" %% "akka-actor" % "2.3.11",
  "com.typesafe.akka" %% "akka-agent" % "2.3.11",
  "com.typesafe.akka" %% "akka-testkit" % "2.3.11" % "test",
  "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test"
)
