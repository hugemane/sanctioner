organization := "com.hugemane"
name := "sanactioner"
version := "0.0.1-SNAPSHOT"

scalaVersion := "2.12.2"

dependencyOverrides ++= Dependencies.dependencyOverrides

libraryDependencies ++= Dependencies.allDependencies

testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-l", "com.hugemane.sanctioner.test.tag.DatabaseTest")

