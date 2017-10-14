import TestDependencies._
import Versions._
import sbt._

object Dependencies {

  val dependencyOverrides = Set(
    "com.typesafe.akka" %% "akka-actor" % "2.5.6",
    "com.typesafe.akka" %% "akka-stream" % "2.5.6"
  )

  val baseDependencies = Seq(
    "com.typesafe" % "config" % "1.3.1",
    "joda-time" % "joda-time" % "2.9.7"
  )

  val dataDependencies = Seq(
    "org.mongodb.scala" %% "mongo-scala-driver" % mongo_version
  )

  val akkaDependencies = Seq(
    "com.typesafe.akka" %% "akka-actor" % akka_version,
    "com.typesafe.akka" %% "akka-stream" % akka_version,
    "com.typesafe.akka" %% "akka-remote" % akka_version,
    "com.typesafe.akka" %% "akka-http" % akka_http_version,
    "com.typesafe.akka" %% "akka-http-xml" % akka_http_version
  )

  val serializationDependencies = Seq(
    "io.circe" %% "circe-core" % circe_version,
    "io.circe" %% "circe-generic" % circe_version,
    "io.circe" %% "circe-parser" % circe_version
  )

  val httpCallDependencies = Seq(
    "org.scalaj" %% "scalaj-http" % "2.3.0"
  )

  val allDependencies = baseDependencies ++
                        dataDependencies ++
                        akkaDependencies ++
                        serializationDependencies ++
                        httpCallDependencies ++
                        serviceTestDependencies
}
