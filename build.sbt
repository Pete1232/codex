lazy val root = (project in file("."))
  .settings(
    name := "cricket-coach",
    version := "1.0",
    scalaVersion := "2.12.3",
    libraryDependencies := Dependencies())
  .configs(IntegrationTest)
  .settings(
    Defaults.itSettings
  )
