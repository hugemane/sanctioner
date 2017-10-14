package com.hugemane.sanctioner.model.core

trait Identifiable {
  var id: Option[String]

  def setId(id: Option[String]): Unit = {
    this.id = id
  }
}
