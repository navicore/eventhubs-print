name := "EventhubsPrint"

fork := true
javaOptions in test ++= Seq(
  "-Xms512M", "-Xmx2048M",
  "-XX:MaxPermSize=2048M",
  "-XX:+CMSClassUnloadingEnabled"
)

parallelExecution in test := false

version := "1.0"

scalaVersion := "2.12.7"
val akkaVersion = "2.5.17"
val akkaHttpVersion = "10.1.5"

libraryDependencies ++=
  Seq(
    "ch.megard" %% "akka-http-cors" % "0.3.1",

    "ch.qos.logback" % "logback-classic" % "1.2.3",
    "com.typesafe" % "config" % "1.3.3",
    "com.typesafe.scala-logging" %% "scala-logging" % "3.9.0",

    "tech.navicore" %% "akkaeventhubs" % "0.9.3",
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-stream" % akkaVersion,
    "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-persistence" % akkaVersion,
    "com.typesafe.akka" %% "akka-protobuf" % akkaVersion,

    "org.json4s" %% "json4s-native" % "3.6.1",
    "com.github.nscala-time" %% "nscala-time" % "2.20.0",

    "org.scalatest" %% "scalatest" % "3.0.5" % "test"

  )

dependencyOverrides ++= Seq(
  "com.typesafe.akka" %% "akka-actor"  % akkaVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-persistence" % akkaVersion,
  "com.typesafe.akka" %% "akka-protobuf" % akkaVersion
)

mainClass in assembly := Some("navicore.akka.eventhubs.Main")
assemblyJarName in assembly := "EventhubsPrint.jar"

