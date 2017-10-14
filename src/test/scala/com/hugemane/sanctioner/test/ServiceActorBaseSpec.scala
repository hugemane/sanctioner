package com.hugemane.sanctioner.test

import akka.actor.ActorSystem
import com.hugemane.sanctioner.repository.init.Repositories
import com.hugemane.sanctioner.service.init.ServiceActors
import com.typesafe.config.Config

abstract class ServiceActorBaseSpec(_system: ActorSystem) extends ActorBaseSpec(_system) {
  def this() = this(ActorSystem())

  override def postBeforeAll(config: Config) {
    Repositories.initialize(config)
    ServiceActors.initialize(system, config)
  }
}
