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

import cats.data.Reader
import org.mongodb.scala.MongoDatabase
import org.mongodb.scala.bson.collection.immutable.Document
import utils.Constants

import scala.concurrent.ExecutionContext

trait Players {
  private val collection = Reader { (db: MongoDatabase) =>
    db.getCollection(Constants.Collections.PLAYERS)
  }

  protected def insertTestDocument(implicit ec: ExecutionContext) = {
    val doc = Document("name" -> "bob")
    collection.map(collection =>
      collection
        .insertOne(doc)
        .head()
        .map(_ => (): Unit)
    )
  }
}
