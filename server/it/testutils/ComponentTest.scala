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

import config.AppLoader
import org.scalatest.{MustMatchers, WordSpec}
import org.scalatestplus.play.{BaseOneAppPerTest, FakeApplicationFactory}
import play.api.{Application, ApplicationLoader, Environment}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Awaitable}

trait ComponentTest extends WordSpec with MustMatchers with BaseOneAppPerTest with FakeApplicationFactory {
  def await[T](awaitable: Awaitable[T], duration: Duration = Duration.Inf): T = Await.result(awaitable, duration)

  lazy val appLoader = new AppLoader

  override def fakeApplication(): Application = appLoader.load(ApplicationLoader.createContext(Environment.simple()))
}
