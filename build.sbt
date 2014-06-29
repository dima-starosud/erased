name := "erased"

version := "1.0"

scalaVersion := "2.11.1"

scalacOptions ++= Seq("-unchecked", "-feature", "-language:higherKinds")

libraryDependencies ++= Seq(
  "junit" % "junit" % "4.11" % "test")

