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
import org.mongodb.scala.MongoDatabase
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._

trait UnitCardService extends UnitCardRepository {

  def getUnitCardAction(request: Request[AnyContent]): Reader[MongoDatabase, IO[JsValue]] = {
    getUnitCardFromCollection()
      .map(_.map(unitCard =>
        Json.toJson(unitCard)
      ))
  }

  def insertUnitCardAction(request: Request[AnyContent]): Option[Reader[MongoDatabase, JsValue]] = {

    val unitCard = request.body.asJson.flatMap {
      _.validate[UnitCard].asOpt
    }

    unitCard map { card =>
      insertUnitCardToCollection(card)
        .map(completed =>
          Json.parse(completed.toString())
        )
    }
  }
}
