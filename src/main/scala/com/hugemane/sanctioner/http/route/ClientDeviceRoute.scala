package com.hugemane.sanctioner.http.route

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.MissingFormFieldRejection
import akka.pattern.ask
import com.hugemane.sanctioner.http.route.core.RouteBase
import com.hugemane.sanctioner.http.wrapper.DataWrapper
import com.hugemane.sanctioner.model.ClientDevice
import com.hugemane.sanctioner.service.actor._
import com.hugemane.sanctioner.service.system.ActorSystemExtender._
import io.circe.generic.auto._
import com.hugemane.sanctioner.utility.StringUtility._

import scala.concurrent.Future

trait ClientDeviceRoute extends RouteBase {

  var clientDeviceServiceCall: (Any) => Future[Any] = (actorMessage: Any) => {
    system.lookup_existing_actor(ClientDeviceActor.name) ? actorMessage
  }

  val clientDeviceRoutes = {
    path("client" / "device" / "list") {
      get {
        parameters('wrap.?) { wrap =>
          onSuccess(clientDeviceServiceCall(ListClientDevices()).mapTo[ListClientDevicesDone]) { results =>
            if (wrap.isDefined) complete(DataWrapper(results.clientDevices)) else complete(results.clientDevices)
          }
        }
      }
    } ~
      path("client" / "device" / Segment) { id =>
        get {
          onSuccess(clientDeviceServiceCall(GetClientDevice(id)).mapTo[GetClientDeviceDone]) { result =>
            complete(result.clientDevice)
          }
        }
      } ~
      path("client" / "device") {
        put {
          entity(as[ClientDevice]) {
            case clientDevice @ fc if !clientDevice.fullName.has_value() => reject(MissingFormFieldRejection("fullName"))
            case clientDevice @ dc if !clientDevice.device.has_value() => reject(MissingFormFieldRejection("device"))
            case clientDevice =>
              onSuccess(clientDeviceServiceCall(CreateClientDevice(clientDevice)).mapTo[CreateClientDeviceDone]) { result =>
                complete(result.clientDevice)
              }
          }
        }
      } ~
      path("client" / "device") {
        post {
          entity(as[ClientDevice]) {
            case clientDevice @ fc if !clientDevice.fullName.has_value() => reject(MissingFormFieldRejection("fullName"))
            case clientDevice @ dc if !clientDevice.device.has_value() => reject(MissingFormFieldRejection("device"))
            case clientDevice =>
              onSuccess(clientDeviceServiceCall(UpdateClientDevice(clientDevice)).mapTo[UpdateClientDeviceDone]) { result =>
                complete(result.clientDevice)
              }
          }
        }
      } ~
      path("client" / "device" / Segment) { id =>
        delete {
          onSuccess(clientDeviceServiceCall(DeleteClientDevice(id)).mapTo[DeleteClientDeviceDone]) { deletedIdResult =>
            complete(deletedIdResult)
          }
        }
      }
  }

}
