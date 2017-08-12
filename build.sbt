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
lazy val testScalastyle = taskKey[Unit]("testScalastyle")
(testScalastyle in Test) := scalastyle.in(Test).toTask("").value
(testScalastyle in IntegrationTest) := scalastyle.in(IntegrationTest).toTask("").value

lazy val root = (project in file("."))
  .settings(
    name := "cricket-coach",
    version := "1.0",
    scalaVersion := "2.12.3",
    libraryDependencies ++= Dependencies())
  .configs(IntegrationTest)
  .settings(
    Defaults.itSettings
  )
  .settings(
    wartremoverErrors ++= Warts.unsafe
  )
  .settings(
    (test in Test) := ((test in Test) dependsOn (testScalastyle in Test)).value,
    (test in IntegrationTest) := ((test in IntegrationTest) dependsOn (testScalastyle in IntegrationTest)).value,
    scalastyleFailOnError := false
  )
  .settings(
    coverageEnabled := true,
    coverageMinimum := 100,
    coverageFailOnMinimum := true
  )
