package com.hugemane.sanctioner.boot.system

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.typesafe.config.Config

class ActorSystemLoader(config: Config) {
  implicit val system: ActorSystem = ActorSystem("sanctioner", config)
  implicit val executor = system.dispatcher
  implicit val materializer = ActorMaterializer()

  def actorSystemInterface: String = {
    config.getString("akka.remote.netty.tcp.hostname")
  }

  def actorSystemPort: Int = {
    config.getInt("akka.remote.netty.tcp.port")
  }
}
