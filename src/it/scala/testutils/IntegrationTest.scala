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
package testutils

import config.AppConfig
import org.mongodb.scala.MongoDatabase
import org.scalatest._
import repositories.utils.Database

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Awaitable, ExecutionContext}


trait IntegrationTest extends fixture.AsyncWordSpec with MustMatchers {

  override type FixtureParam = MongoDatabase

  override def withFixture(test: OneArgAsyncTest): FutureOutcome = {

    object TestDatabase extends Database {
      val db = database.run(AppConfig)
    }

    complete {
      withFixture(test.toNoArgAsyncTest(TestDatabase.db))
    } lastly {
      await {
        TestDatabase.db.drop().head().map(_ => (): Unit)
      }
    }

  }

  implicit val ec: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global

  def await[T](awaitable: Awaitable[T]): T = Await.result(awaitable, Duration.Inf)
}
