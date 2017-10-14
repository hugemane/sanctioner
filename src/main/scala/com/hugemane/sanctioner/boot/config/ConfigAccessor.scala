package com.hugemane.sanctioner.boot.config

import com.typesafe.config.Config

trait ConfigAccessor {
  implicit val config: Config
}
