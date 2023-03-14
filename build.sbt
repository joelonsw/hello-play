ThisBuild / scalaVersion := "2.13.8"

ThisBuild / version := "1.0-SNAPSHOT"

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name := """hello-play""",
    libraryDependencies ++= Seq(
      guice,
      ws,
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test,
      "mysql" % "mysql-connector-java" % "8.0.32",
      "org.playframework.anorm" %% "anorm" % "2.6.9",
      "com.typesafe.play" %% "play-slick" % "5.0.0",
      "com.typesafe.play" %% "play-slick-evolutions" % "5.0.0",
    )
  )