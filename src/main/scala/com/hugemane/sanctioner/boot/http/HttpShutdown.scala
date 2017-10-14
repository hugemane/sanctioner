package com.hugemane.sanctioner.boot.http

import akka.http.scaladsl.Http.ServerBinding
import com.hugemane.sanctioner.service.system.ActorSystemAccessor

import scala.concurrent.{ Await, ExecutionContextExecutor, Future }
import scala.concurrent.duration.Duration

trait HttpShutdown extends ActorSystemAccessor {
  implicit val executor: ExecutionContextExecutor

  def shutdownHttpServer(httpBinder: Future[ServerBinding]) {
    println("Shutting down http server...")

    httpBinder
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate())

    Await.result(system.whenTerminated, Duration.Inf)

    println("Http server shutdown")
  }
}
