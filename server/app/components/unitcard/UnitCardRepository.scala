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

import cats.Eval
import cats.data.Reader
import cats.effect.IO
import org.mongodb.scala.{Completed, MongoCollection, MongoDatabase}
import utils.Constants

import scala.concurrent.ExecutionContext

trait UnitCardRepository {

  private val getCollection: Reader[MongoDatabase, MongoCollection[UnitCard]] =
    Reader { db: MongoDatabase =>
      db.getCollection[UnitCard](Constants.Collections.PLAYERS)
    }

  def getUnitCardFromCollection()(implicit ec: ExecutionContext): Reader[MongoDatabase, IO[UnitCard]] =
    for {
      collection <- getCollection
    } yield {
      IO.fromFuture(Eval.later(
        collection.find().head()
      ))
    }

  def insertUnitCardToCollection(unitCard: UnitCard)
                                          (implicit ec: ExecutionContext): Reader[MongoDatabase, IO[Completed]] =
      for {
        collection <- getCollection
      } yield {
        IO.fromFuture(Eval.later(
          collection.insertOne(unitCard).head()
        ))
      }
}
