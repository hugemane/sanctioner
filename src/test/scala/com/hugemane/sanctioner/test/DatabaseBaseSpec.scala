package com.hugemane.sanctioner.test

import com.hugemane.sanctioner.repository.provider.DataProviderSettingsLoader
import com.hugemane.sanctioner.repository.provider.mongo.MongoDataProvider
import com.typesafe.config.ConfigFactory
import org.scalatest.BeforeAndAfterAll

trait DatabaseBaseSpec extends TestBaseSpec with BeforeAndAfterAll {
  protected var internalDataProvider: MongoDataProvider = _

  def dataProvider: MongoDataProvider = {
    if (internalDataProvider == null) {
      val config = ConfigFactory.load()
      val settings = DataProviderSettingsLoader(config).load()
      internalDataProvider = MongoDataProvider(settings)
    }
    internalDataProvider
  }

  override def afterAll {
    dataProvider.close()
  }
}