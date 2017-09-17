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
package components.unitcard

import cats.effect.IO
import org.mongodb.scala.Completed
import play.api.libs.json.Json
import play.api.mvc.{AnyContent, AnyContentAsJson, Request}
import play.api.test.FakeRequest
import play.api.test.Helpers._
import testutils.ControllerUnitTest

import scala.concurrent.ExecutionContext

class UnitCardControllerSpec extends ControllerUnitTest {

  private lazy val service = mock[UnitCardService]
  private lazy val controller = new UnitCardController(service, mockControllerComponents)

  "getUnitCard" must {
    s"return $OK" in {
      forAll { name: String =>
        val req = FakeRequest("GET", "/codex/unit")

        val testJson = Json.obj("name" -> name)

        (service.getUnitCardAsJson(_: ExecutionContext)) expects * returning IO(testJson)

        val result = controller.getUnitCard(req)

        status(result) mustBe OK
      }
    }
    "display the input json" in {
      forAll { name: String =>
        val req = FakeRequest("GET", "/codex/unit")

        val testJson = Json.obj("name" -> name)

        (service.getUnitCardAsJson(_: ExecutionContext)) expects * returning IO(testJson)

        val result = controller.getUnitCard(req)

        contentAsJson(result) mustBe testJson
      }
    }
  }

  "insertUnitCard" must {
    s"return $OK if the card was inserted" in {
      forAll { name: String =>
        val testUnitCard = UnitCard(name)
        val testJson = Json.obj("result" -> Completed().toString())

        val request = FakeRequest("POST", "/codex/unit")
          .withBody(AnyContentAsJson(Json.toJson(testUnitCard)))

        (service.insertUnitCardFromRequest(_: Request[AnyContent])(_: ExecutionContext)) expects(request, *) returning Some(IO(testJson))

        val result = controller.insertUnitCard().apply(request)

        status(result) mustBe OK
      }
    }
    "display the returned json if the card was inserted" in {
      forAll { name: String =>
        val testUnitCard = UnitCard(name)
        val testJson = Json.obj("result" -> Completed().toString())

        val request = FakeRequest("POST", "/codex/unit")
          .withBody(AnyContentAsJson(Json.toJson(testUnitCard)))

        (service.insertUnitCardFromRequest(_: Request[AnyContent])(_: ExecutionContext)) expects(request, *) returning Some(IO(testJson))

        val result = controller.insertUnitCard().apply(request)

        contentAsJson(result) mustBe testJson
      }
    }
    s"return $BAD_REQUEST if the card was not insered" in {
      forAll { name: String =>
        val testUnitCard = UnitCard(name)
        val testJson = Json.obj("result" -> Completed().toString())

        val request = FakeRequest("POST", "/codex/unit")
          .withBody(AnyContentAsJson(Json.toJson(testUnitCard)))

        (service.insertUnitCardFromRequest(_: Request[AnyContent])(_: ExecutionContext)) expects(request, *) returning None

        val result = controller.insertUnitCard().apply(request)

        status(result) mustBe BAD_REQUEST
      }
    }
  }
}
