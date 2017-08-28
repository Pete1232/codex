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
import play.api.libs.json.Json
import play.api.test.FakeRequest
import testutils.ControllerUnitTest
import play.api.test.Helpers._

class UnitCardControllerSpec extends ControllerUnitTest with UnitCardController {

  override protected def controllerComponents = mockControllerComponents

  "buildUnitCardAction" must {
    s"return $OK" in {
      forAll { name: String =>
        val req = FakeRequest("GET", "/codex/unit")

        val testJson = Json.obj("name" -> name)

        val Some(action) = buildUnitCardAction.run(IO(testJson)).unsafeRunTimed(defaultTimeout)

        val result = await {
          action(req)
        }

        result.header.status mustBe OK
      }
    }
    "display the input json" in {
      forAll { name: String =>
        val req = FakeRequest("GET", "/codex/unit")

        val testJson = Json.obj("name" -> name)

        val Some(action) = buildUnitCardAction.run(IO(testJson)).unsafeRunTimed(defaultTimeout)

        val result = action(req)

        contentAsJson(result) mustBe testJson
      }
    }
  }
}
