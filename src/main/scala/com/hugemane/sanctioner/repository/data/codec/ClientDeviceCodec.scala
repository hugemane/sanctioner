package com.hugemane.sanctioner.repository.data.codec

import com.hugemane.sanctioner.model.ClientDevice
import com.hugemane.sanctioner.repository.data.core.codec.CodecBase
import org.bson.codecs.{ Codec, DecoderContext, EncoderContext }
import org.bson.{ BsonReader, BsonType, BsonWriter }
import org.joda.time.DateTime

class ClientDeviceCodec extends Codec[ClientDevice] with CodecBase {

  override def encode(writer: BsonWriter, value: ClientDevice, encoderContext: EncoderContext): Unit = {
    writer.writeStartDocument()
    writeOptionObjectId(writer, value.id)
    writeOptionString(writer, "fullName", value.fullName)
    writeOptionString(writer, "device", value.device)
    writeOptionDateTime(writer, "created", value.created)
    writer.writeEndDocument()
  }

  override def getEncoderClass: Class[ClientDevice] = classOf[ClientDevice]

  override def decode(reader: BsonReader, decoderContext: DecoderContext): ClientDevice = {
    var id: Option[String] = None
    var fullName: Option[String] = None
    var device: Option[String] = None
    var created: Option[DateTime] = None

    reader.readStartDocument()

    while (reader.readBsonType ne BsonType.END_OF_DOCUMENT) {
      val fieldName = reader.readName

      fieldName match {
        case "_id" => id = readOptionObjectId(reader)
        case "fullName" => fullName = readOptionString(reader)
        case "device" => device = readOptionString(reader)
        case "created" => created = readOptionDateTime(reader)
      }
    }

    reader.readEndDocument()

    ClientDevice(id, fullName, device, created)
  }
}
