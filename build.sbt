
lazy val commonSettings = Seq(
  organization := "bookshelf",
  version := "1.0",
  scalaVersion := "2.11.7",
  scalacOptions ++= Seq(
    "-deprecation",
    "-feature",
    "-unchecked",
    "-Xfatal-warnings",
    "-Xlint:_"
  ),
  cancelable in Global := true,
  fork := true
)

lazy val mine = project
  .in(file("mine"))
  .settings(commonSettings: _*)
  .settings(
    name := "Mine",
    javaOptions += "-Xmx4G"
  )

lazy val saloon = project
  .in(file("saloon"))
  .dependsOn(mine)
  .settings(commonSettings: _*)
  .settings(
    name := "Saloon",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http-experimental" % "2.4.2",
      "org.postgresql" % "postgresql" % "9.4-1206-jdbc41",
      "io.getquill" %% "quill-jdbc" % "0.4.1",
      "io.getquill" %% "quill-async" % "0.4.1",
      "com.github.nscala-time" %% "nscala-time" % "2.10.0"
    )
  )

