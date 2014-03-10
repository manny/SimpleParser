import sbt._
import com.twitter.sbt._

class SampleProject(info: ProjectInfo) extends StandardProject(info) with SubversionPublisher {
  lazy val print = task {log.info("a test action"); None}
  val jackson = "org.codehaus.jackson" % "jackson-core-asl" % "1.6.1"
  val specs = "org.scala-tools.testing" % "specs_2.8.0" % "1.6.5" % "test"

  override def subversionRepository = Some("http://svn.local.twitter.com/maven/")
}
