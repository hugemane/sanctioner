package com.hugemane.sanctioner.test

import akka.actor.ActorSystem
import akka.testkit.{ ImplicitSender, TestKit }
import com.typesafe.config.{ Config, ConfigFactory }
import org.scalatest.{ BeforeAndAfterAll, FlatSpecLike, Matchers }

import scala.concurrent.Await
import scala.concurrent.duration.Duration

abstract class ActorBaseSpec(_system: ActorSystem) extends TestKit(_system) with ImplicitSender with Matchers with FlatSpecLike with BeforeAndAfterAll {

  override def beforeAll() {
    val config = ConfigFactory.load()
    postBeforeAll(config)
  }

  def postBeforeAll(config: Config)

  override def afterAll {
    terminateActorSystem()
  }

  protected def terminateActorSystem() {
    system.terminate()
    Await.result(system.whenTerminated, Duration.Inf)
  }
}
