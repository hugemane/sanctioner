package com.hugemane.sanctioner.http.route.core

import com.hugemane.sanctioner.http.serialization.JsonCirceCompleteSerialization
import com.hugemane.sanctioner.service.system.ActorSystemAccessor

trait RouteBase extends ActorSystemAccessor with JsonCirceCompleteSerialization