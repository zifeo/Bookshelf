name := "Bookshelf"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http-experimental" % "2.4.2",
  "org.postgresql" % "postgresql" % "9.4-1206-jdbc41",
  "io.getquill" %% "quill-jdbc" % "0.4.1",
  "io.getquill" %% "quill-async" % "0.4.1"
)