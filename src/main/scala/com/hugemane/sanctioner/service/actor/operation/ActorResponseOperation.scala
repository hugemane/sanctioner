package com.hugemane.sanctioner.service.actor.operation

import akka.actor.Actor

import scala.concurrent.Future

trait ActorResponseOperation extends Actor with ActorResponseBuilder {
  implicit val ec = context.dispatcher

  def handleOperation[T](operation: Future[T], responseMessage: (T) => Any): Unit = {
    val requester = sender()
    operation.map { x => requester ! buildResponse(x, responseMessage) }
  }

  def handleBulkOperation[T](operation: Future[Seq[T]], responseMessage: (Seq[T]) => Any): Unit = {
    val requester = sender()
    operation.map { x => requester ! responseMessage(x) }
  }
}