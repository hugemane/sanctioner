package com.hugemane.sanctioner.repository.init

import com.hugemane.sanctioner.repository.data.ClientDeviceRepository
import com.hugemane.sanctioner.repository.init.core.RepositoryInitializer
import com.typesafe.config.Config

object Repositories extends RepositoryInitializer {

  override def initialize(config: Config) {
    super.initialize(config)

    repositories = Map[String, AnyRef](
      "ClientDeviceRepository" -> new ClientDeviceRepository(dataProvider))
  }

}
