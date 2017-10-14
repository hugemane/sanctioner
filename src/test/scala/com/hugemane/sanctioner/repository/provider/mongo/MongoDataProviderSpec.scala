package com.hugemane.sanctioner.repository.provider.mongo

import com.hugemane.sanctioner.repository.provider.DataProviderSettingsLoader
import com.hugemane.sanctioner.test.TestBaseSpec
import com.hugemane.sanctioner.test.tag.DatabaseTest
import com.typesafe.config.ConfigFactory

class MongoDataProviderSpec extends TestBaseSpec {

  it should "connect to database with mongodb client" taggedAs DatabaseTest in {
    val config = ConfigFactory.load()
    val databaseSettings = DataProviderSettingsLoader(config).load()
    MongoDataProvider(databaseSettings)
    //todo: HUGE HERE -> need some kind of assertion
  }
}
