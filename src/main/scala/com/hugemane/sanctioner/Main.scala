package com.hugemane.sanctioner

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import com.hugemane.sanctioner.boot.BasicServiceBoot
import com.hugemane.sanctioner.boot.http.HttpStartup
import com.hugemane.sanctioner.http.HttpRoutes
import com.hugemane.sanctioner.repository.init.Repositories
import com.hugemane.sanctioner.service.init.ServiceActors
import com.typesafe.config.Config

import scala.concurrent.ExecutionContextExecutor

object Main extends BasicServiceBoot {

  override def loadRepositoryDependencies(config: Config): Unit = {
    Repositories.initialize(config)
  }

  override def loadServiceAkkaDependencies(system: ActorSystem, config: Config): Unit = {
    ServiceActors.initialize(system, config)
  }

  override def loadHttpServer(configuration: Config, actorSystem: ActorSystem, systemExecutor: ExecutionContextExecutor, systemMaterializer: ActorMaterializer): Unit = {
    class HttpStartupLoader extends HttpStartup with HttpRoutes {
      override implicit val config: Config = configuration
      override implicit val system: ActorSystem = actorSystem
      override implicit val executor: ExecutionContextExecutor = systemExecutor
      override implicit val materializer: ActorMaterializer = systemMaterializer

      override def httpRoutes: Route = allHttpRoutes
    }
    new HttpStartupLoader().startHttpServer()
  }
}