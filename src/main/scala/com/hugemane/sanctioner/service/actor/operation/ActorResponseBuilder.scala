package com.hugemane.sanctioner.service.actor.operation

trait ActorResponseBuilder {

  def buildResponse[T](entity: T, responseMessage: (T) => Any): Any = {
    if (responseMessage == null) return entity
    responseMessage(entity)
  }
}
