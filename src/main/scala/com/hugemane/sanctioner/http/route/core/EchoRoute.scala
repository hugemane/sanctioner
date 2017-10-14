package com.hugemane.sanctioner.http.route.core

import akka.http.scaladsl.model.{ ContentTypes, HttpEntity }
import akka.http.scaladsl.server.Directives._
import com.hugemane.sanctioner.utility.DateTimeUtility._
import org.joda.time.DateTime

trait EchoRoute extends RouteBase {

  val echoRoutes = {
    path("echo") {
      val dateTimeNow = DateTime.now
      val echoResponse = s"""{"echo":"${dateTimeNow.to_value()}"}"""
      complete(HttpEntity(ContentTypes.`application/json`, echoResponse))
    }
  }

}
