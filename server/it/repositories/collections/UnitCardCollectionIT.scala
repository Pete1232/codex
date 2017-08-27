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
package repositories.collections

import repositories.models.UnitCard
import testutils.IntegrationTest

class UnitCardCollectionIT extends IntegrationTest with UnitCardCollection {
  "insertTestDocument" must {
    "insert" in { db =>
      insertTestDocument().run(db).unsafeRunTimed(defaultTimeout) mustBe Some((): Unit)
    }
  }

  "getUnitCard" must {
    "return the first saved UnitCard from the database" in { db =>
      insertTestDocument().run(db).unsafeRunTimed(defaultTimeout)

      val Some(res) = getUnitCard.run(db).unsafeRunTimed(defaultTimeout)

      res mustBe UnitCard("bob")
    }
  }
}
