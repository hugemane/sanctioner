package com.hugemane.sanctioner.boot

trait BasicServiceBoot extends ServiceBoot {

  init()

  loadActorSystem(config)

  loadRepositoryDependencies(config)

  loadServiceAkkaDependencies(system, config)

  loadHttpServer(config, system, executor, materializer)

}
