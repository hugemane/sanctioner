package com.hugemane.sanctioner.repository.data.core.codec

import org.bson.BsonWriter
import org.joda.time.DateTime
import com.hugemane.sanctioner.utility.StringUtility._
import com.hugemane.sanctioner.repository.data.core.ObjectIdExtender._

trait CodecWriter {

  def writeOptionObjectId(writer: BsonWriter, id: Option[String]) {
    if (id.isDefined) writer.writeObjectId("_id", id.to_object_id)
  }

  def writeOptionString(writer: BsonWriter, field: String, value: Option[String]) {
    if (value.has_value()) writer.writeString(field, value.get)
  }

  def writeOptionDateTime(writer: BsonWriter, field: String, value: Option[DateTime]) {
    if (value.isDefined) writer.writeDateTime(field, value.get.toDate.getTime)
  }
}
