package com.hugemane.sanctioner.repository.init

import com.hugemane.sanctioner.repository.data.ClientDeviceRepository
import com.hugemane.sanctioner.repository.provider.mongo.MongoDataProvider
import com.hugemane.sanctioner.test.TestBaseSpec
import com.hugemane.sanctioner.test.tag.DatabaseTest
import com.typesafe.config.ConfigFactory

class RepositorySpec extends TestBaseSpec {

  it should "initialize data provider from config with initialized repositories based on class names" taggedAs DatabaseTest in {
    val config = ConfigFactory.load()
    Repositories.initialize(config)

    val repository = Repositories.getRepository[ClientDeviceRepository]()

    repository should not be null
  }

  it should "have access to data provider" taggedAs DatabaseTest in {
    val config = ConfigFactory.load()
    Repositories.initialize(config)

    val dataProvider = Repositories.getDataProvider

    dataProvider should not be null
    dataProvider.getClass shouldEqual classOf[MongoDataProvider]
  }
}
