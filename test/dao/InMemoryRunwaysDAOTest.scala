package dao

import org.scalatest.{FlatSpec, Matchers}
import parser.CsvRunwayParser

import scala.concurrent.ExecutionContext.Implicits.global
import scala.language.reflectiveCalls

/**
  * Created by tmnd on 12/03/17.
  */
class InMemoryRunwaysDAOTest extends FlatSpec with Matchers {
  val fixture = new {
    val dao: RunwaysDAO = new InMemoryRunwaysDAO(new CsvRunwayParser())
  }

  it should "return the correct 10 least used identifiers for runways with count" in {
    fixture.dao.mostUsedIdentifiers(10, Ordering.Int) should contain theSameElementsAs (
      ("08U", 1) :: ("\"16R\"", 1) :: ("H02", 1) :: ("N80E", 1) :: ("04B", 1) :: ("110", 1) :: ("132", 1) ::
        ("13G", 1) :: ("01H", 1) :: ("H10", 1) :: Nil)
  }

  it should "return the correct 10 most used identifiers for runways with count" in {
    fixture.dao.mostUsedIdentifiers(10, Ordering.Int.reverse) should contain theSameElementsAs(
      ("H1", 5566) :: ("18", 3180) :: ("09", 2581) :: ("17", 2320) :: ("16", 1559) :: ("12", 1506) :: ("14", 1469) ::
        ("08", 1459) :: ("13", 1447) :: ("15", 1399) :: Nil
    )
  }
}