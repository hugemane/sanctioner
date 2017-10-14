package com.hugemane.sanctioner.model

import com.hugemane.sanctioner.model.core.Identifiable
import com.hugemane.sanctioner.model.core.Created
import org.joda.time.DateTime

case class ClientDevice(
  var id: Option[String],
  fullName: Option[String],
  device: Option[String],
  created: Option[DateTime]) extends Identifiable with Created