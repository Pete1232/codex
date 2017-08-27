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
import sbt._

object Dependencies {

  private val scalatestPlusPlay = "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.1"

  private lazy val compile = Seq(
    "org.typelevel" %% "cats-core" % "1.0.0-MF",
    "org.typelevel" %% "cats-effect" % "0.4",
    "com.chuusai" %% "shapeless" % "2.3.2",
    "org.mongodb.scala" %% "mongo-scala-driver" % "2.1.0",
    "org.scalactic" %% "scalactic" % "3.0.1",
    "com.typesafe" % "config" % "1.3.1"
  )

  private lazy val test = Seq(
    scalatestPlusPlay,
    "org.scalamock" %% "scalamock-scalatest-support" % "3.6.0",
    "org.scalacheck" %% "scalacheck" % "1.13.4"
  ).map(_ % Test)

  private lazy val it = Seq(
    scalatestPlusPlay
  ).map(_ % IntegrationTest)

  def apply(): Seq[ModuleID] = compile ++ test ++ it
}
