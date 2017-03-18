package dao

import org.scalatest.{FlatSpec, Matchers}
import parser.{CsvAirportParser, CsvCountryParser, CsvRunwayParser}
import scala.language.reflectiveCalls
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by tmnd on 12/03/17.
  */
class InMemoryCountriesDAOTest extends FlatSpec with Matchers {
  val fixture = new {
    val dao: CountriesDAO = new InMemoryCountriesDAO(new CsvAirportParser(), new CsvRunwayParser(), new CsvCountryParser())
  }
  it should "return 200 airports when looking for ita" in {
    val optResult = fixture.dao.findAirportsWithRunways("ita")
    optResult.isDefined should be(true)
    optResult.get._1.name should be("Italy")
    val result = optResult.get._2
    result.size should be(200)
    val linate = result.find(_._1.id == 4345)
    linate should not be (None)
    linate.get._2.size should be(2)
  }


  it should "return Ukraine when looking for country uk" in {
    val optResult = fixture.dao.findCountry("uk")
    optResult.isDefined should be(true)
    optResult.get.name should be("Ukraine")
  }

  it should "return nothing when looking for the empty string" in {
    val optResult = fixture.dao.findCountry("")
    optResult should be(None)
  }

  it should "list the correct runway types for Italy" in {
    val cOpt = fixture.dao.findCountry("ita")
    cOpt.isDefined should be(true)
    fixture.dao.runwayTypeForEachCountry()(cOpt.get) should contain theSameElementsAs ("Concrete" :: "Dirt" ::
      "Asphalt" :: "Grass" :: "" :: "Ground" :: "Asp" :: "Unk" :: "Grs" :: "Gre" :: "Bituminous" :: "Bit" :: Nil)
  }

  it should "list Tokelau, Pitcairn South Georgia, Vatican City, Andorra, Norfolk, Tuvalu, BIOT, Cocos and Aruba " +
    " when looking for the 10 countries with least airports" in {
    fixture.dao.topCountriesForAirportCount(10, Ordering.Int).map(_._1.code) should contain theSameElementsAs (
      "TK" :: "PN" :: "GS" :: "VA" :: "AD" :: "NF" :: "TV" :: "IO" :: "CC" :: "AW" :: Nil
      )
  }

  it should "list United States, Brazil, Canada, Australia, Russia, France, Argentina, Germany, Colombia and Venezuela" +
    " when looking for the 10 countries with most airports" in {
    fixture.dao.topCountriesForAirportCount(10, Ordering.Int.reverse).map(_._1.code) should contain theSameElementsAs(
      "US" :: "BR" :: "CA" :: "AU" :: "RU" :: "FR" :: "AR" :: "DE" :: "CO" :: "VE" :: Nil
    )
  }
}