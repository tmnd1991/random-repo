package parser

import java.nio.file.Paths

import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by tmnd on 09/03/17.
  */
class CsvCountryParserTest extends FlatSpec with Matchers {
  it should "correctly parse the example file" in {
    val parser = new CsvCountryParser()
    val output = parser.parse(Paths.get("resources/countries.csv"))
    val s = output.toStream
    s.size should be (247)
  }

}
