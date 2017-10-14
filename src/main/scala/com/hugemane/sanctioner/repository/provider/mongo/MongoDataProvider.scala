package com.hugemane.sanctioner.repository.provider.mongo

import com.hugemane.sanctioner.repository.provider.DataProviderSettings
import org.mongodb.scala.{ MongoClient, MongoCollection, MongoDatabase }

import scala.reflect.ClassTag

class MongoDataProvider(mongoClient: MongoClient, database: MongoDatabase) {
  private var collections = Map[String, MongoCollection[_]]()

  def collection[T](name: String)(implicit c: ClassTag[T]): MongoCollection[T] = {
    if (!collections.contains(name)) collections = collections + (name -> database.getCollection[T](name))
    collections(name).asInstanceOf[MongoCollection[T]]
  }

  def close() {
    if (mongoClient != null) mongoClient.close()
  }
}

object MongoDataProvider {
  def apply(settings: DataProviderSettings): MongoDataProvider = {
    val mongoClient: MongoClient = MongoClient(settings.url)
    val database: MongoDatabase = mongoClient.getDatabase(settings.database)
    new MongoDataProvider(mongoClient, database)
  }
}
