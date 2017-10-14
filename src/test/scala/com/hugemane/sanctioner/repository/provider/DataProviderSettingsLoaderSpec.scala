package com.hugemane.sanctioner.repository.provider

import com.hugemane.sanctioner.test.TestBaseSpec
import com.typesafe.config.{ ConfigException, ConfigFactory }

class DataProviderSettingsLoaderSpec extends TestBaseSpec {

  it should "load database provider settings from config" in {
    val config = ConfigFactory.load()
    val databaseSettings = DataProviderSettingsLoader(config).load()
    databaseSettings.url shouldEqual "mongodb://localhost"
    databaseSettings.database shouldEqual "sanctioner"
  }

  it should "raise exception when database url is not configured" in {
    an[ConfigException] shouldBe thrownBy {
      val config = ConfigFactory.load("nodb.application.conf")
      DataProviderSettingsLoader(config).load()
    }
  }
}
