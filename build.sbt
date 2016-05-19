
scalaVersion := "2.11.8"

lazy val commonSettings = Seq(
  organization := "bookshelf",
  version := "1.0",
  scalaVersion := "2.11.8",
  scalacOptions ++= Seq(
    "-deprecation",
    "-feature",
    "-unchecked",
    "-Xlint:_"
  ),
  libraryDependencies ++= Seq(
    "com.typesafe" % "config" % "1.3.0",
    "ch.qos.logback" % "logback-classic" % "1.1.7",
    "com.github.nscala-time" %% "nscala-time" % "2.12.0",
    "org.scalatest" %% "scalatest" % "2.2.6" % "test"
  ),
  unmanagedClasspath in Compile += baseDirectory.value / "src" / "main" / "resources",
  cancelable in Global := true,
  fork := true
)

lazy val mine = project
  .in(file("mine"))
  .settings(commonSettings: _*)
  .settings(
    name := "Mine",
    libraryDependencies ++= Seq(
      "org.postgresql" % "postgresql" % "9.4-1206-jdbc41",
      "io.getquill" %% "quill-jdbc" % "0.6.0"
    ),
    javaOptions += "-Xmx4G"
  )

lazy val saloon = project
  .in(file("saloon"))
  .dependsOn(mine)
  .settings(commonSettings: _*)
  .settings(
    name := "Saloon",
    libraryDependencies ++= Seq(
      "io.getquill" %% "quill-async" % "0.6.0",
      "com.typesafe.akka" %% "akka-slf4j" % "2.4.4",
      "com.typesafe.akka" %% "akka-http-experimental" % "2.4.4",
      "com.typesafe.akka" %% "akka-http-spray-json-experimental" % "2.4.4",
      "com.typesafe.akka" %% "akka-http-testkit-experimental" % "2.4.2-RC3" % "test"
    ),
    assemblyJarName in assembly := "bookshelf-saloon.jar",
    test in assembly := {},
    mainClass in assembly := Some("bookshelf.saloon.Main")
  )

