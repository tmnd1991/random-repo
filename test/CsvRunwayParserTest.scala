import java.nio.file.Paths

import org.scalatest.{FlatSpec, Matchers}
import parser.CsvRunwayParser

/**
  * Created by tmnd on 09/03/17.
  */
class CsvRunwayParserTest extends FlatSpec with Matchers {
  it should "correctly parse the example file" in {
    val parser = new CsvRunwayParser()
    val output = parser.parse(Paths.get("/Users/tmnd/IdeaProjects/lunatech_code_assesment/resources/runways.csv"))
    val s = output.toStream
    s.size should be(39536)
  }
}
