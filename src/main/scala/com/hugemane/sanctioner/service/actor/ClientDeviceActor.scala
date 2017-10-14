package com.hugemane.sanctioner.service.actor

import akka.actor.{ Actor, Props }
import com.hugemane.sanctioner.model.ClientDevice
import com.hugemane.sanctioner.repository.data.ClientDeviceRepository
import com.hugemane.sanctioner.repository.init.Repositories
import com.hugemane.sanctioner.service.actor.operation.ActorResponseOperation

case class CreateClientDevice(clientDevice: ClientDevice)
case class CreateClientDeviceDone(clientDevice: ClientDevice)

case class UpdateClientDevice(clientDevice: ClientDevice)
case class UpdateClientDeviceDone(clientDevice: ClientDevice)

case class DeleteClientDevice(id: String)
case class DeleteClientDeviceDone(id: Option[String])

case class GetClientDevice(id: String)
case class GetClientDeviceDone(clientDevice: Option[ClientDevice], id: String)

case class ListClientDevices()
case class ListClientDevicesDone(clientDevices: Seq[ClientDevice])

class ClientDeviceActor extends Actor with ActorResponseOperation {
  private[this] val repository = Repositories.getRepository[ClientDeviceRepository]()

  def receive = {
    case CreateClientDevice(clientDevice) =>
      val operation = repository.create(clientDevice)
      handleOperation(operation, CreateClientDeviceDone)

    case UpdateClientDevice(clientDevice) =>
      val operation = repository.update(clientDevice)
      handleOperation(operation, UpdateClientDeviceDone)

    case DeleteClientDevice(id) =>
      val requester = sender()
      val operation = repository.deleteForId(id)
      operation.map(x => requester ! DeleteClientDeviceDone(x))

    case GetClientDevice(id) =>
      val requester = sender()
      val operation = repository.findById(Some(id))
      operation.map(x => requester ! GetClientDeviceDone(x, id))

    case ListClientDevices() =>
      val operation = repository.findAll()
      handleBulkOperation(operation, ListClientDevicesDone)
  }
}

object ClientDeviceActor {
  def props() = Props[ClientDeviceActor]
  val name = "client-device"
}