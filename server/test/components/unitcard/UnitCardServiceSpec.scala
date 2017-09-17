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
import play.api.libs.json.{JsString, Json}
import play.api.mvc.AnyContentAsJson
import play.api.test.FakeRequest
import testutils.UnitTest

import scala.concurrent.ExecutionContext

class UnitCardServiceSpec extends UnitTest {

  private lazy val repository = mock[UnitCardRepository]
  private lazy val service = new UnitCardService(repository)

  "getUnitCardAsJson" must {
    "convert a given UnitCard to a JsObject" in {
      forAll { name: String =>
        val testUnitCard = UnitCard(name)

        (repository.getUnitCardFromCollection()(_: ExecutionContext)) expects * returning IO(testUnitCard)

        val Some(result) = service.getUnitCardAsJson.unsafeRunTimed(defaultTimeout)

        result mustBe Json.obj("name" -> name)
      }
    }
  }
  "insertUnitCardFromRequest" must {
    "return the correct json body if the card was saved correctly" in {
      forAll { name: String =>
        val testUnitCard = UnitCard(name)

        val request = FakeRequest("POST", "/codex/unit")
          .withBody(AnyContentAsJson(Json.toJson(testUnitCard)))

        (repository.insertUnitCardToCollection(_: UnitCard)(_: ExecutionContext)) expects(testUnitCard, *) returning IO(Completed())

        val Some(result) = service
          .insertUnitCardFromRequest(request)
          .flatMap(_.unsafeRunTimed(defaultTimeout))

        result mustBe Json.obj("result" -> Completed().toString())
      }
    }
  }
}
