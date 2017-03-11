name := """skeleton"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

resolvers += Resolver.bintrayRepo("zamblauskas", "maven")

libraryDependencies += "co.fs2" %% "fs2-core" % "0.9.4"

libraryDependencies += "co.fs2" %% "fs2-io" % "0.9.4"

libraryDependencies += "zamblauskas" %% "scala-csv-parser" % "0.11.4"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test
)

// scalastyle

scalastyleFailOnError := true

org.scalastyle.sbt.ScalastylePlugin.projectSettings ++
  Seq(
    org.scalastyle.sbt.ScalastylePlugin.scalastyleConfig in Compile := file("project/scalastyle-config.xml"),
    org.scalastyle.sbt.ScalastylePlugin.scalastyleConfig in Test := file("project/test-scalastyle-config.xml")
  )