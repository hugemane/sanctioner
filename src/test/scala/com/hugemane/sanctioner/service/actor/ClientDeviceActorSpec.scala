package com.hugemane.sanctioner.service.actor

import com.hugemane.sanctioner.model.ClientDevice
import com.hugemane.sanctioner.test.ServiceActorBaseSpec
import com.hugemane.sanctioner.service.system.ActorSystemExtender._
import com.hugemane.sanctioner.test.tag.IntegrationTest

class ClientDeviceActorSpec extends ServiceActorBaseSpec {

  it should "create client device" taggedAs IntegrationTest in {
    val actor = system.lookup_existing_actor(ClientDeviceActor.name)

    val clientDeviceToCreate = ClientDevice(None, Some("hugemane"), Some("laptop"), None)
    actor ! CreateClientDevice(clientDeviceToCreate)

    expectMsgPF() {
      case CreateClientDeviceDone(clientDevice) =>
        clientDevice.fullName shouldEqual Some("hugemane")
        clientDevice.device shouldEqual Some("laptop")
    }
  }

  it should "list all client devices" taggedAs IntegrationTest in {
    val actor = system.lookup_existing_actor(ClientDeviceActor.name)

    actor ! ListClientDevices()

    expectMsgPF() {
      case ListClientDevicesDone(clientDevices) =>
        clientDevices.size should be > 1
    }
  }

}
