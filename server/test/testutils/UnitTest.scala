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

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import org.scalamock.scalatest.MockFactory
import org.scalatest._
import org.scalatest.prop.GeneratorDrivenPropertyChecks

import scala.concurrent.duration.{Duration, FiniteDuration, _}
import scala.concurrent.{Await, Awaitable, ExecutionContext}
import scala.language.postfixOps

trait UnitTest extends WordSpec with MustMatchers with MockFactory with OneInstancePerTest with GeneratorDrivenPropertyChecks {
  implicit val actorSystem: ActorSystem = ActorSystem()
  implicit val mat: ActorMaterializer = ActorMaterializer()
  implicit val ec: ExecutionContext = ExecutionContext.global

  def await[T](awaitable: Awaitable[T], duration: Duration = Duration.Inf): T = Await.result(awaitable, duration)

  protected val defaultTimeout: FiniteDuration = 1 second
}
