package com.hugemane.sanctioner.boot

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.hugemane.sanctioner.boot.dependency.DependencyLoader
import com.hugemane.sanctioner.boot.system.ActorSystemLoader
import com.typesafe.config.{ Config, ConfigFactory }

import scala.concurrent.ExecutionContextExecutor

trait ServiceBoot extends App with DependencyLoader {
  var config: Config = _
  var system: ActorSystem = _
  var executor: ExecutionContextExecutor = _
  var materializer: ActorMaterializer = _

  def init(): Unit = {
    config = ConfigFactory.load()
  }

  def loadActorSystem(config: Config): Unit = {
    val actorSystemLoader = new ActorSystemLoader(config)

    println(s"LOADED actor system> ${actorSystemLoader.actorSystemInterface}:${actorSystemLoader.actorSystemPort}")

    system = actorSystemLoader.system
    executor = actorSystemLoader.executor
    materializer = actorSystemLoader.materializer
  }

  def loadHttpServer(configuration: Config, actorSystem: ActorSystem, systemExecutor: ExecutionContextExecutor, systemMaterializer: ActorMaterializer): Unit
}
