package com.hugemane.sanctioner.model.core

import org.joda.time.DateTime

trait Created {
  def created: Option[DateTime]
}
