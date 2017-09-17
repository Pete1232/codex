// Copyright (C) 2011-2012 the original author or authors.
// See the LICENCE.txt file distributed with this work for additional
// information regarding copyright ownership.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package config

import components.healthcheck.HealthcheckController
import components.unitcard.UnitCardComponent
import database.DatabaseProvider
import play.api.ApplicationLoader.Context
import play.api.{Application, ApplicationLoader, BuiltInComponentsFromContext, LoggerConfigurator}
import play.filters.HttpFiltersComponents
import router.Routes

import scala.concurrent.ExecutionContext

class AppLoader extends ApplicationLoader {

  class AppComponents(context: Context) extends BuiltInComponentsFromContext(context)
    with HttpFiltersComponents
    with controllers.AssetsComponents {

    private lazy val ec: ExecutionContext = ExecutionContext.global

    private lazy val db = new DatabaseProvider(AppConfig).database

    private lazy val healthcheckController = new HealthcheckController(controllerComponents)
    private lazy val unitCardController = new UnitCardComponent(controllerComponents, db)(ec).controller

    private lazy val codexRoutes = new codex.Routes(httpErrorHandler, unitCardController)

    lazy val router = new Routes(httpErrorHandler, healthcheckController, assets, codexRoutes)
  }

  def load(context: Context): Application = {
    LoggerConfigurator(context.environment.classLoader).foreach {
      _.configure(context.environment, context.initialConfiguration, Map.empty)
    }
    new AppComponents(context).application
  }
}
