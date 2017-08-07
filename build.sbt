lazy val root = (project in file("."))
  .configs(IntegrationTest)
  .settings(Defaults.itSettings: _*)
  .settings(
    name := "galaxy-merchant-guide",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := "2.12.2"
  )
  .settings(
    mainClass in assembly := Some("pellis.Main"),
    assemblyJarName in assembly := "galaxy-merchant-guide.jar"
  )
  .settings(libraryDependencies += "com.github.scopt" %% "scopt" % "3.6.0")
  .settings(libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test")
