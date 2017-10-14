package com.hugemane.sanctioner.http

import akka.http.scaladsl.server.Directives._
import com.hugemane.sanctioner.http.route.ClientDeviceRoute
import com.hugemane.sanctioner.http.route.core.HttpRoutesBase

trait HttpRoutes extends HttpRoutesBase with ClientDeviceRoute {

  allHttpRoutes = allHttpRoutes ~ clientDeviceRoutes

}
