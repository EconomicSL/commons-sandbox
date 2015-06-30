logLevel := Level.Warn

// code coverage
resolvers += Classpaths.sbtPluginReleases
addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.1.0")
addSbtPlugin("org.scoverage" % "sbt-coveralls" % "1.0.0")

// scalastyle
resolvers += "sonatype-releases" at "https://oss.sonatype.org/content/repositories/releases/"
addSbtPlugin("org.scalastyle" %% "scalastyle-sbt-plugin" % "0.7.0")
