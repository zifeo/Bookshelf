
lazy val commonSettings = Seq(
  organization := "bookshelf",
  version := "1.0",
  scalaVersion := "2.11.8",
  scalacOptions ++= Seq(
    "-deprecation",
    "-feature",
    "-unchecked",
    "-Xfatal-warnings",
    "-Xlint:_"
  ),
  libraryDependencies ++= Seq(
    "org.postgresql" % "postgresql" % "9.4-1206-jdbc41",
    "io.getquill" %% "quill-jdbc" % "0.4.1",
    "io.getquill" %% "quill-async" % "0.4.1",
    "ch.qos.logback" % "logback-classic" % "1.1.6"
  ),
  cancelable in Global := true,
  fork := true
)

lazy val mine = project
  .in(file("mine"))
  .settings(commonSettings: _*)
  .settings(
    name := "Mine",
    javaOptions += "-Xmx4G",
    libraryDependencies ++= Seq(
      "org.scalatest" % "scalatest_2.11" % "2.2.6"
    )
  )

lazy val saloon = project
  .in(file("saloon"))
  .dependsOn(mine)
  .settings(commonSettings: _*)
  .settings(
    name := "Saloon",
    libraryDependencies ++= Seq(
      "com.typesafe" % "config" % "1.3.0",
      "com.typesafe.akka" %% "akka-http-experimental" % "2.4.2",
      "com.typesafe.akka" %% "akka-http-spray-json-experimental" % "2.4.2",
      "com.github.nscala-time" %% "nscala-time" % "2.10.0"
    ),
    assemblyJarName in assembly := "bookshelf-saloon.jar",
    test in assembly := {},
    mainClass in assembly := Some("bookshelf.saloon.Main")
  )

