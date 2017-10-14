package com.hugemane.sanctioner.boot

import com.hugemane.sanctioner.Main
import com.hugemane.sanctioner.test.TestBaseSpec
import com.hugemane.sanctioner.test.tag.IntegrationTest

import scalaj.http._

class MainSpec extends TestBaseSpec {

  it should "boot up service with akka and http interface" taggedAs IntegrationTest in {
    Main

    //wait 2 seconds until the system has booted
    Thread.sleep(2000)

    val response: HttpResponse[String] = Http("http://127.0.0.1:8000/echo").asString
    val echoResponse = response.body

    echoResponse should startWith("""{"echo":""")
    echoResponse should endWith regex """\d{2}\.\d{2}\.\d{4} \d{2}:\d{2}:\d{2}"}$"""
  }
}
