package com.hugemane.sanctioner.http.route.core

import akka.http.scaladsl.model.ContentTypes._
import akka.http.scaladsl.model.StatusCodes
import akka.util.ByteString
import com.hugemane.sanctioner.test.RouteBaseSpec

class EchoRouteSpec extends RouteBaseSpec with EchoRoute {

  it should "get echo with date time of request" in {
    Get("/echo") ~> echoRoutes ~> check {
      status.isSuccess() shouldBe true
      status shouldEqual StatusCodes.OK
      contentType shouldEqual `application/json`

      val result = responseAs[ByteString].decodeString("UTF-8")

      result should startWith("""{"echo":""")
      result should endWith regex """\d{2}\.\d{2}\.\d{4} \d{2}:\d{2}:\d{2}"}$"""
    }
  }

}
