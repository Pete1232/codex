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
package repositories.utils

import cats.data.Reader
import com.mongodb.MongoCredential._
import config.AppConfig
import org.mongodb.scala.connection.ClusterSettings

import scala.collection.JavaConverters._

trait Database {

  import org.mongodb.scala._

  private val credential: Reader[AppConfig, MongoCredential] = Reader { (config: AppConfig) =>
    import config._
    createScramSha1Credential(
      mongoUser,
      mongoAuthDbName,
      mongoPassword.toCharArray,
    )
  }

  private val clusterSettings = Reader { (config: AppConfig) =>
    ClusterSettings.builder()
      .hosts(List(new ServerAddress(config.mongoHost)).asJava)
      .build()
  }

  private val client =
    for {
      cred <- credential
      cluster <- clusterSettings
    } yield {
      MongoClientSettings.builder()
        .clusterSettings(cluster)
        .credentialList(List(cred).asJava)
        .build()
    }

  protected val database = client.map(settings =>
    MongoClient(clientSettings = settings)
      .getDatabase("cc")
  )
}
