package com.hugemane.sanctioner.repository.data

import com.hugemane.sanctioner.model.ClientDevice
import com.hugemane.sanctioner.repository.data.codec.ClientDeviceCodec
import com.hugemane.sanctioner.repository.data.core.Repository
import com.hugemane.sanctioner.repository.provider.mongo.MongoDataProvider
import org.joda.time.DateTime
import com.hugemane.sanctioner.repository.data.core.RepositoryUpdateExtender._
import org.mongodb.scala.model.Filters._
import org.mongodb.scala.model.Updates._
import com.hugemane.sanctioner.repository.data.core.ObjectIdExtender._

import scala.concurrent.{ ExecutionContext, Future }

class ClientDeviceRepository(val dataProvider: MongoDataProvider) extends Repository[ClientDevice] {
  val collection = initializeCollection("client_device", new ClientDeviceCodec)

  override def create(clientDevice: ClientDevice)(implicit executionContext: ExecutionContext): Future[ClientDevice] = {
    val idized = clientDevice.copy(id = getDefaultId(), created = Some(DateTime.now))
    super.create(idized)
  }

  def update(clientDevice: ClientDevice)(implicit executionContext: ExecutionContext): Future[ClientDevice] = {
    val updateOperations = Seq(
      clientDevice.fullName.update_field("fullName"),
      clientDevice.device.update_field("device"))
    val operation = collection.updateOne(equal("_id", clientDevice.id.to_object_id), combine(updateOperations: _*)).toFuture()
    operation.map(_ => clientDevice)
  }

}
