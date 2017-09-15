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
package database

import components.unitcard.UnitCard
import org.bson.codecs.configuration.CodecRegistry

object Codecs {
  def buildCodecs(): CodecRegistry = {
    import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
    import org.mongodb.scala.bson.codecs.DEFAULT_CODEC_REGISTRY
    import org.mongodb.scala.bson.codecs.Macros._
    fromRegistries(fromProviders(classOf[UnitCard]), DEFAULT_CODEC_REGISTRY)
  }

}
