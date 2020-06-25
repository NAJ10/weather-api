lazy val akkaHttpVersion = "10.1.12"
lazy val akkaVersion    = "2.6.6"

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization    := "com.gamma",
      scalaVersion    := "2.13.2"
    )),
    name := "weather-api",
    libraryDependencies ++= Seq(
      "com.typesafe.akka"      %% "akka-http"                % akkaHttpVersion,
      "com.typesafe.akka"      %% "akka-http-spray-json"     % akkaHttpVersion,
      "com.typesafe.akka"      %% "akka-actor"               % akkaVersion,
      "com.typesafe.akka"      %% "akka-stream"              % akkaVersion,
      "com.zaxxer"              %  "HikariCP"                 % "3.4.5",
      "org.flywaydb"            %  "flyway-core"              % "6.4.4",
      "org.postgresql"          % "postgresql"                % "42.2.14",
      "ch.qos.logback"          % "logback-classic"           % "1.2.3",
      "org.playframework.anorm" %% "anorm"                    % "2.6.5",
      "com.typesafe.akka"  %% "akka-http-testkit"        % akkaHttpVersion % Test,
      "com.typesafe.akka"  %% "akka-testkit"             % akkaVersion     % Test,
      "org.scalatest"      %% "scalatest"                % "3.2.0"         % Test
    )
  )
