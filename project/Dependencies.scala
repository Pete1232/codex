import sbt._

object Dependencies {

  private val circeVersion = "0.8.0"

  private val scalatest = "org.scalatest" %% "scalatest" % "3.0.1"

  private lazy val circe = Seq(
    "io.circe" %% "circe-core",
    "io.circe" %% "circe-generic",
    "io.circe" %% "circe-parser"
  ).map(_ % circeVersion)

  private lazy val compile = Seq(
    "org.typelevel" %% "cats-core" % "1.0.0-MF",
    "org.typelevel" %% "cats-effect" % "0.4",
    "com.chuusai" %% "shapeless" % "2.3.2",
    "org.mongodb.scala" %% "mongo-scala-driver" % "2.1.0",
    "org.scalactic" %% "scalactic" % "3.0.1"
  ) ++ circe

  private lazy val test = Seq(
    scalatest,
    "org.scalamock" %% "scalamock-scalatest-support" % "3.6.0",
    "org.scalacheck" %% "scalacheck" % "1.13.4"
  ).map(_ % Test)

  private lazy val it = Seq(
    scalatest
  ).map(_ % IntegrationTest)

  def apply(): Seq[ModuleID] = compile ++ test ++ it
}
