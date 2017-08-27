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

import play.api.http.Status
import play.api.test.FakeRequest
import testutils.ControllerUnitTest

class UnitCardControllerSpec extends ControllerUnitTest {
  lazy val controller = new UnitCardController(mockControllerComponents)

  "getUnitCard" must {
    s"be TODO" in {

      val req = FakeRequest("GET", "/codex/unit")

      val res = await {
        controller.getUnitCard(req)
      }

      res.header.status mustBe Status.NOT_IMPLEMENTED
    }
  }
}
