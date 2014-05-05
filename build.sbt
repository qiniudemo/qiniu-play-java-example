name := "qiniu-play"

version := "0.1"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "com.qiniu" % "sdk" % "6.1.2"
)

play.Project.playJavaSettings
