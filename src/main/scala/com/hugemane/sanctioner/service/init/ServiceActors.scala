package com.hugemane.sanctioner.service.init

import akka.actor.ActorSystem
import com.hugemane.sanctioner.service.actor.ClientDeviceActor
import com.typesafe.config.Config

object ServiceActors {

  def initialize(system: ActorSystem, config: Config): Unit = {
    system.actorOf(ClientDeviceActor.props(), ClientDeviceActor.name)
  }

}
