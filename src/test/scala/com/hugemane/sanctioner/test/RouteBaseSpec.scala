package com.hugemane.sanctioner.test

import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.BeforeAndAfterAll

trait RouteBaseSpec extends TestBaseSpec with ScalatestRouteTest with BeforeAndAfterAll
