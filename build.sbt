name := "net-pass-be"

version := "0.1"

scalaVersion := "2.13.3"


libraryDependencies ++= {
  val akkaHttpV         = "10.2.1"
  val akkaV             = "2.6.10"
  val scalaTestV        = "3.2.2"
  Seq(
    "com.typesafe.akka" %% "akka-actor"           % akkaV,
    "com.typesafe.akka" %% "akka-stream"          % akkaV,
    "com.typesafe.akka" %% "akka-http"            % akkaHttpV,
    "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpV,
    "com.typesafe.akka" %% "akka-http-xml"        % akkaHttpV,
    "com.typesafe.akka" %% "akka-http-testkit"    % akkaHttpV  % Test,
    "com.typesafe.akka" %% "akka-testkit"         % akkaV      % Test,
    "com.typesafe.akka" %% "akka-stream-testkit"  % akkaV      % Test,
    "org.scalatest"     %% "scalatest"            % scalaTestV   % Test
  )
}
