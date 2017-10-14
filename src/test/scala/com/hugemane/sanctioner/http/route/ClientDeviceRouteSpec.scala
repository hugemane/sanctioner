package com.hugemane.sanctioner.http.route

import akka.http.scaladsl.model.ContentTypes._
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model.{ HttpEntity, StatusCodes }
import akka.http.scaladsl.server.{ MethodRejection, MissingFormFieldRejection }
import akka.util.ByteString
import com.hugemane.sanctioner.model.ClientDevice
import com.hugemane.sanctioner.service.actor._
import com.hugemane.sanctioner.test.RouteBaseSpec
import com.hugemane.sanctioner.utility.DateTimeUtility._

import scala.concurrent.Future

class ClientDeviceRouteSpec extends RouteBaseSpec with ClientDeviceRoute {

  it should "list all client devices" in {
    val clientDevice1 = ClientDevice(Some("59e21b8356a3160e09c1c684"), Some("hugemane"), Some("laptop"), Some("14.10.2017 16:18:22".to_date()))
    val clientDevice2 = ClientDevice(Some("59e21ede56a31614e661ec1e"), Some("hugemane"), Some("desktop"), Some("14.10.2017 16:28:43".to_date()))
    clientDeviceServiceCall = (_) => { Future { ListClientDevicesDone(Seq(clientDevice1, clientDevice2)) } }

    Get("/client/device/list") ~> clientDeviceRoutes ~> check {
      status.isSuccess() shouldBe true
      status shouldEqual StatusCodes.OK
      contentType shouldEqual `application/json`
      responseAs[ByteString] shouldEqual ByteString("""[{"id":"59e21b8356a3160e09c1c684","fullName":"hugemane","device":"laptop","created":"14.10.2017 16:18:22"},{"id":"59e21ede56a31614e661ec1e","fullName":"hugemane","device":"desktop","created":"14.10.2017 16:28:43"}]""")
    }
  }

  it should "list all client devices wrapped in data entity" in {
    val clientDevice1 = ClientDevice(Some("59e21b8356a3160e09c1c684"), Some("hugemane"), Some("laptop"), Some("14.10.2017 16:18:22".to_date()))
    val clientDevice2 = ClientDevice(Some("59e21ede56a31614e661ec1e"), Some("hugemane"), Some("desktop"), Some("14.10.2017 16:28:43".to_date()))
    clientDeviceServiceCall = (_) => { Future { ListClientDevicesDone(Seq(clientDevice1, clientDevice2)) } }

    Get("/client/device/list?wrap=true") ~> clientDeviceRoutes ~> check {
      status.isSuccess() shouldBe true
      status shouldEqual StatusCodes.OK
      contentType shouldEqual `application/json`
      responseAs[ByteString] shouldEqual ByteString("""{"data":[{"id":"59e21b8356a3160e09c1c684","fullName":"hugemane","device":"laptop","created":"14.10.2017 16:18:22"},{"id":"59e21ede56a31614e661ec1e","fullName":"hugemane","device":"desktop","created":"14.10.2017 16:28:43"}]}""")
    }
  }

  it should "get client device for supplied id" in {
    val clientDevice = ClientDevice(Some("59e21b8356a3160e09c1c684"), Some("hugemane"), Some("laptop"), Some("14.10.2017 16:18:22".to_date()))
    clientDeviceServiceCall = (_) => { Future { GetClientDeviceDone(Some(clientDevice), "59e21b8356a3160e09c1c684") } }

    Get("/client/device/59e21b8356a3160e09c1c684") ~> clientDeviceRoutes ~> check {
      status.isSuccess() shouldBe true
      status shouldEqual StatusCodes.OK
      contentType shouldEqual `application/json`
      responseAs[ByteString] shouldEqual ByteString("""{"id":"59e21b8356a3160e09c1c684","fullName":"hugemane","device":"laptop","created":"14.10.2017 16:18:22"}""")
    }
  }

  it should "create client device" in {
    val clientDevice = ClientDevice(Some("59e21b8356a3160e09c1c684"), Some("hugemane"), Some("laptop"), Some("14.10.2017 16:18:22".to_date()))
    clientDeviceServiceCall = (_) => { Future { CreateClientDeviceDone(clientDevice) } }

    val jsonContent = HttpEntity(`application/json`, """{"fullName":"hugemane","device":"laptop"}""")

    Put("/client/device", jsonContent) ~> clientDeviceRoutes ~> check {
      status.isSuccess() shouldBe true
      status shouldEqual StatusCodes.OK
      contentType shouldEqual `application/json`
      responseAs[ByteString] shouldEqual ByteString("""{"id":"59e21b8356a3160e09c1c684","fullName":"hugemane","device":"laptop","created":"14.10.2017 16:18:22"}""")
    }
  }

  it should "raise exception when client device does not supply fullName during creation" in {
    clientDeviceServiceCall = (_) => { Future {} }

    val jsonContent = HttpEntity(`application/json`, """{"device":"laptop"}""")

    Put("/client/device", jsonContent) ~> clientDeviceRoutes ~> check {
      rejection shouldEqual MissingFormFieldRejection("fullName")
    }
  }

  it should "raise exception when client device does not supply device during creation" in {
    clientDeviceServiceCall = (_) => { Future {} }

    val jsonContent = HttpEntity(`application/json`, """{"fullName":"hugemane"}""")

    Put("/client/device", jsonContent) ~> clientDeviceRoutes ~> check {
      rejection shouldEqual MissingFormFieldRejection("device")
    }
  }

  it should "update client device" in {
    val clientDevice = ClientDevice(Some("59e21b8356a3160e09c1c684"), Some("hugemane"), Some("desktop"), Some("14.10.2017 16:18:22".to_date()))
    clientDeviceServiceCall = (_) => { Future { UpdateClientDeviceDone(clientDevice) } }

    val jsonContent = HttpEntity(`application/json`, """{"fullName":"hugemane","device":"desktop"}""")

    Post("/client/device", jsonContent) ~> clientDeviceRoutes ~> check {
      status.isSuccess() shouldBe true
      status shouldEqual StatusCodes.OK
      contentType shouldEqual `application/json`
      responseAs[ByteString] shouldEqual ByteString("""{"id":"59e21b8356a3160e09c1c684","fullName":"hugemane","device":"desktop","created":"14.10.2017 16:18:22"}""")
    }
  }

  it should "delete client device for supplied id" in {
    clientDeviceServiceCall = (_) => { Future { DeleteClientDeviceDone(Some("59e21b8356a3160e09c1c684")) } }

    Delete("/client/device/59e21b8356a3160e09c1c684") ~> clientDeviceRoutes ~> check {
      status.isSuccess() shouldBe true
      status shouldEqual StatusCodes.OK
      contentType shouldEqual `application/json`
      responseAs[ByteString] shouldEqual ByteString("""{"id":"59e21b8356a3160e09c1c684"}""")
    }
  }

  it should "reject delete client device request when id parameter is missing" in {
    clientDeviceServiceCall = (_) => { Future {} }

    Delete("/client/device") ~> clientDeviceRoutes ~> check {
      // since id is not specified, post is supported for the route but PUT and POST method(s) only supported for this URI
      rejections shouldEqual List(MethodRejection(PUT), MethodRejection(POST))
    }
  }

}
