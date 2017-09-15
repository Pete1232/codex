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

import cats.data.Reader
import cats.effect.IO
import play.api.libs.json.{JsObject, Json}
import repositories.models.UnitCard

import scala.concurrent.ExecutionContext

trait UnitCardService {
  protected def convertUnitCardToJson()(implicit ec: ExecutionContext): Reader[IO[UnitCard], IO[JsObject]] =
    Reader { (unitCardIO: IO[UnitCard]) =>
      unitCardIO map (unitCard =>
        Json.obj("name" -> unitCard.name) // TODO json parsing library? Use Mongo codecs?
        )
    }
}
