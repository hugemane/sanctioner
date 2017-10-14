package com.hugemane.sanctioner.service.system

import akka.actor.ActorSystem
import akka.util.Timeout

import scala.concurrent.duration._

trait ActorSystemAccessor {

  implicit val system: ActorSystem

  implicit val askTimeout: Timeout = 10.seconds

}
