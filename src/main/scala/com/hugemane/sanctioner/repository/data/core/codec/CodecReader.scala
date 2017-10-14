package com.hugemane.sanctioner.repository.data.core.codec

import org.bson.BsonReader
import org.joda.time.DateTime

trait CodecReader {

  def readOptionObjectId(reader: BsonReader): Option[String] = {
    Some(reader.readObjectId().toHexString)
  }

  def readOptionString(reader: BsonReader): Option[String] = {
    Some(reader.readString())
  }

  def readOptionDateTime(reader: BsonReader): Option[DateTime] = {
    Some(new DateTime(reader.readDateTime()))
  }

}
