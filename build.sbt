name := """Snorlax"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  filters,
  "com.google.maps" % "google-maps-services" % "0.1.15",
  "com.maxmind.geoip2" % "geoip2" % "2.8.0-rc1",
  "com.fasterxml.jackson.module" % "jackson-module-scala_2.11" % "2.8.2"
)
