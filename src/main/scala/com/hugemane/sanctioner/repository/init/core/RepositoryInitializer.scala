package com.hugemane.sanctioner.repository.init.core

import com.hugemane.sanctioner.repository.provider.DataProviderSettingsLoader
import com.hugemane.sanctioner.repository.provider.mongo.MongoDataProvider
import com.typesafe.config.Config

import scala.reflect.ClassTag

trait RepositoryInitializer {
  protected var dataProvider: MongoDataProvider = _
  protected var repositories: Map[String, _] = _

  def getRepository[T: ClassTag]()(implicit m: Manifest[T]) = {
    val repositoryName = m.runtimeClass.getSimpleName
    repositories(repositoryName).asInstanceOf[T]
  }

  def initialize(config: Config) {
    val settings = DataProviderSettingsLoader(config).load()
    dataProvider = MongoDataProvider(settings)
  }

  def getDataProvider: MongoDataProvider = dataProvider
}
