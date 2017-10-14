package com.hugemane.sanctioner.boot.dependency

import akka.actor.ActorSystem
import com.typesafe.config.Config

trait DependencyLoader {

  def loadRepositoryDependencies(config: Config): Unit

  def loadServiceAkkaDependencies(system: ActorSystem, config: Config): Unit

}
