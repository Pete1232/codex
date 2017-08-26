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
val APP_NAME = "codex"
val SCALA_VERSION = "2.12.3"

lazy val server = (project in file("server"))
  .enablePlugins(PlayScala)
  .configs(IntegrationTest)
  .settings(
    scalaVersion := SCALA_VERSION,
    libraryDependencies ++= Dependencies()
  )
  .settings(
    wartremoverErrors ++= Warts.unsafe,
    wartremoverExcluded += baseDirectory.value / "app" / "repositories" / "utils" / "Codecs.scala"
  )
  .settings(
    Defaults.itSettings,
    sourceDirectory in IntegrationTest := baseDirectory.value / "it",
    unmanagedSourceDirectories in IntegrationTest += (sourceDirectory in IntegrationTest).value
  )
  .settings(
    test in Test := ((test in Test) dependsOn scalastyle.in(Compile).toTask("")).value,
    test in IntegrationTest := ((test in IntegrationTest) dependsOn scalastyle.in(Compile).toTask("")).value,
    scalastyleFailOnError := true
  )
  .settings(
    coverageEnabled := true,
    coverageMinimum := 100,
    coverageFailOnMinimum := true,
    coverageExcludedPackages := "utils.Constants"
  )
