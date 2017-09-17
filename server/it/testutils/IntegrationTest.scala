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

import cats.Eval
import cats.effect.IO
import config.AppConfig
import database.DatabaseProvider
import org.mongodb.scala.MongoDatabase
import org.scalatest._

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._
import scala.language.postfixOps

trait IntegrationTest extends fixture.WordSpec with MustMatchers with TestDatabase with BeforeAndAfterAll {

  override type FixtureParam = MongoDatabase

  private def dropDatabase = {
    IO.fromFuture(Eval.later(
      db.drop().head().map(_ => (): Unit)
    ))
  }

  override def beforeAll(): Unit = {
    super.beforeAll()
    dropDatabase.unsafeRunTimed(defaultTimeout)
  }

  override def afterAll(): Unit = {
    dropDatabase.unsafeRunTimed(defaultTimeout)
    super.afterAll()
  }

  override def withFixture(test: OneArgTest): Outcome = {
    try {
      withFixture(test.toNoArgTest(db))
    } finally {
      dropDatabase.unsafeRunSync()
    }
  }

  implicit val ec: ExecutionContext = scala.concurrent.ExecutionContext.global

  protected val defaultTimeout: FiniteDuration = 1 second
}
