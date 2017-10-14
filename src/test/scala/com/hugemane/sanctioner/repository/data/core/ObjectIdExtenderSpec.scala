package com.hugemane.sanctioner.repository.data.core

import com.hugemane.sanctioner.test.TestBaseSpec
import ObjectIdExtender._

class ObjectIdExtenderSpec extends TestBaseSpec with RepositoryEntityIdentity {

  it should "convert object id entity to string value" in {
    val objectId = generateNewObjectId()
    val objectIdAsString = objectId.to_string
    objectIdAsString should not be empty
  }

  it should "convert string object id into object id entity" in {
    val objectIdAsString = "59e1f83456a316501272b576"
    val objectId = objectIdAsString.to_object_id
    objectId should not be null
    objectId.getTimestamp shouldEqual 1507981364
  }

  it should "not convert null string" in {
    val nullStringValue = null
    val objectId = nullStringValue.asInstanceOf[String].to_object_id
    objectId shouldEqual null
  }

  it should "convert option string object id into object id entity" in {
    val optionalObjectIdAsString = Some("59e1f8d956a3165182a0c8b9")
    val objectId = optionalObjectIdAsString.to_object_id
    objectId should not be null
    objectId.getTimestamp shouldEqual 1507981529
  }
}
