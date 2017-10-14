organization := "com.hugemane"
name := "sanactioner"
version := "0.0.1-SNAPSHOT"

scalaVersion := "2.12.2"

dependencyOverrides ++= Dependencies.dependencyOverrides

libraryDependencies ++= Dependencies.allDependencies

//test related
parallelExecution in Test := false

testOptions in Test += Tests.Argument("-l", "DatabaseTest")
testOptions in Test += Tests.Argument("-l", "IntegrationTest")

