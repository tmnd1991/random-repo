import java.nio.file.{Path, Paths}

import org.scalatest.{FlatSpec, Matchers}
import parser.CsvAirportParser

/**
  * Created by tmnd on 09/03/17.
  */
class CsvAirportParserTest extends FlatSpec with Matchers {
  it should "correctly parse the example file" in {
    val parser = new CsvAirportParser()
    val output = parser.parse(Paths.get("/Users/tmnd/IdeaProjects/lunatech_code_assesment/resources/airports.csv"))
    val s = output.toStream
    s.size should be (46505)
  }

}
