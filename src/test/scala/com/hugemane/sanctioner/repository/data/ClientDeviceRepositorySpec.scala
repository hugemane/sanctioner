package com.hugemane.sanctioner.repository.data

import com.hugemane.sanctioner.model.ClientDevice
import com.hugemane.sanctioner.test.DatabaseBaseSpec
import com.hugemane.sanctioner.test.tag.DatabaseTest

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

class ClientDeviceRepositorySpec extends DatabaseBaseSpec {
  lazy val repository = new ClientDeviceRepository(dataProvider)

  it should "create client device" taggedAs DatabaseTest in {
    val clientDevice = ClientDevice(None, Some("hugemane"), Some("laptop"), None)
    val created = repository.create(clientDevice)

    //blocking only for testing
    val result = Await.result(created, 1.second)

    result.fullName.get shouldEqual "hugemane"
    result.device.get shouldEqual "laptop"
    result.id should not be None
  }

  it should "update client device" taggedAs DatabaseTest in {
    val existing = Await.result(repository.findAll(), 1.second).head
    val beforeUpdate = existing.copy(fullName = Some("John Smith"), device = Some("desktop"))

    val afterUpdate = repository.update(beforeUpdate)

    //blocking only for testing
    val result = Await.result(afterUpdate, 1.second)

    result.fullName.get shouldEqual "John Smith"
    result.device.get shouldEqual "desktop"
    result.id shouldEqual beforeUpdate.id
  }

  it should "find client device by id" taggedAs DatabaseTest in {
    val existing = Await.result(repository.findAll(), 1.second).head

    val findById = repository.findById(existing.id)

    //blocking only for testing
    val result = Await.result(findById, 1.second).get

    result.fullName should not be None
    result.device should not be None
    result.id shouldEqual existing.id
  }

  it should "delete client device for id" taggedAs DatabaseTest in {
    val existing = Await.result(repository.findAll(), 1.second).head

    val deletedId = repository.deleteForId(existing.id.get)

    //blocking only for testing
    val result = Await.result(deletedId, 1.second).get

    result should not be None
    result shouldEqual existing.id.get
  }
}
