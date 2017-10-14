package com.hugemane.sanctioner.repository.data.core

import com.hugemane.sanctioner.model.core.Identifiable
import com.hugemane.sanctioner.repository.provider.mongo.MongoDataProvider
import org.bson.codecs.Codec
import org.bson.codecs.configuration.{ CodecRegistries, CodecRegistry }
import org.mongodb.scala.bson.conversions.Bson
import org.mongodb.scala.model.Filters.equal
import org.mongodb.scala.model.Updates.combine
import org.mongodb.scala.{ MongoClient, MongoCollection }
import com.hugemane.sanctioner.repository.data.core.ObjectIdExtender._

import scala.concurrent.{ ExecutionContext, Future }
import scala.reflect.ClassTag

trait Repository[T <: Identifiable] extends RepositoryEntityIdentity {
  val dataProvider: MongoDataProvider

  def initializeCollection[T: ClassTag](collectionName: String, codec: Codec[_]): MongoCollection[T] = {
    val codecRegistry: CodecRegistry = CodecRegistries.fromRegistries(CodecRegistries.fromCodecs(codec), MongoClient.DEFAULT_CODEC_REGISTRY)
    dataProvider.collection[T](collectionName).withCodecRegistry(codecRegistry)
  }

  def collection: MongoCollection[T]

  def create(entity: T)(implicit executionContext: ExecutionContext): Future[T] = {
    entity.setId(getDefaultId())
    val operation = collection.insertOne(entity).toFuture()
    operation.map(_ => entity)
  }

  def updateConfirm(entityId: Option[String], updateOperations: Seq[Bson])(implicit executionContext: ExecutionContext): Future[Boolean] = {
    val operation = collection.updateOne(equal("_id", entityId.to_object_id), combine(updateOperations: _*)).toFuture()
    operation.map(x => x.wasAcknowledged())
  }

  def findById(id: Option[String])(implicit executionContext: ExecutionContext, ct: ClassTag[T]): Future[Option[T]] = {
    if (id.isEmpty) return Future {
      None
    }
    val operation = collection.find[T](equal("_id", id.to_object_id)).toFuture()
    operation.map(x => x.headOption)
  }

  def findAll()(implicit executionContext: ExecutionContext, ct: ClassTag[T]): Future[Seq[T]] = {
    collection.find[T]().toFuture()
  }

  def delete(entity: T)(implicit executionContext: ExecutionContext): Future[Option[String]] = {
    val operation = collection.deleteOne(equal("_id", entity.id.to_object_id)).toFuture()
    operation.map { x => if (x.wasAcknowledged()) Some(entity.id.toString) else None }
  }

  def deleteForId(id: String)(implicit executionContext: ExecutionContext): Future[Option[String]] = {
    val operation = collection.deleteOne(equal("_id", id.to_object_id)).toFuture()
    operation.map { x => if (x.wasAcknowledged()) Some(id) else None }
  }
}
