package com.hugemane.sanctioner.repository.data.core

import org.bson.types.ObjectId
import com.hugemane.sanctioner.repository.data.core.ObjectIdExtender._

trait RepositoryEntityIdentity {

  def getDefaultId(): Option[String] = Some(generateNewObjectId().to_string)

  def generateNewObjectId(): ObjectId = ObjectId.get
}
