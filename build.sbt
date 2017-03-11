name := """random-repo"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test
)


scalastyleFailOnError := true

org.scalastyle.sbt.ScalastylePlugin.projectSettings ++
  Seq(
    org.scalastyle.sbt.ScalastylePlugin.scalastyleConfig in Compile := file("project/scalastyle-config.xml"),
    org.scalastyle.sbt.ScalastylePlugin.scalastyleConfig in Test := file("project/test-scalastyle-config.xml")
  )