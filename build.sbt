name := """lunatech-code-assessment"""

version := "1.0"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

resolvers += Resolver.bintrayRepo("zamblauskas", "maven")

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