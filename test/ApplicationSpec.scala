import dao.{CountriesDAO, InMemoryCountriesDAO}
import dto._
import org.scalatestplus.play._
import parser.{CsvAirportParser, CsvCountryParser, CsvRunwayParser}
import dto.format.implicits._
import model.Airport
import play.api.test._
import play.api.test.Helpers._

import scala.language.reflectiveCalls

/**
  * Add your spec here.
  * You can mock out a whole application including requests, plugins etc.
  * For more information, consult the wiki.
  */
class ApplicationSpec extends PlaySpec with OneAppPerSuite {

  val fixture = new {

    import scala.concurrent.ExecutionContext.Implicits.global

    val dao: CountriesDAO = new InMemoryCountriesDAO(new CsvAirportParser(), new CsvRunwayParser(), new CsvCountryParser())
  }


  "Routes" should {

    "send 404 on a bad request" in {
      route(app, FakeRequest(GET, "/boum")).map(status(_)) mustBe Some(NOT_FOUND)
    }

  }

  "HomeController" should {

    "render the index page" in {
      val home = route(app, FakeRequest(GET, "/")).get

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include("Your new application is ready.")
    }

  }


  //  GET        /v1/airports                 controllers.AirportsController.airportsForCountry(country: String)
  "AirportsController" should {
    "return all the airports of a given specific country" in {
      val italianAirports = route(app, FakeRequest(GET, "/v1/airports?country=Italy")).get
      status(italianAirports) mustBe OK
      contentType(italianAirports) mustBe Some("application/json")
      val countryAndAirports = contentAsJson(italianAirports).as[CountryAirportsDTO]
      countryAndAirports.name mustBe "Italy"
      countryAndAirports.airports.size mustBe 200
    }
  }


  "CountriesController" should {
    //  GET        /v1/countries                controllers.CountriesController.countriesRankBy(query: String, order: String, count: Int ?= 10)
    "list Tokelau, Pitcairn South Georgia, Vatican City, Andorra, Norfolk, Tuvalu, BIOT, Cocos and Aruba " +
      " when looking for the countries with least airports" in {
      val leastAirports = route(app, FakeRequest(GET, "/v1/countries?query=airports&order=asc")).get
      status(leastAirports) mustBe OK
      contentType(leastAirports) mustBe Some("application/json")
      val countriesAndCount = contentAsJson(leastAirports).as[Seq[CountryAndCountDTO]]
      countriesAndCount.map(_.iso) must contain theSameElementsAs (
        "TK" :: "PN" :: "GS" :: "VA" :: "AD" :: "NF" :: "TV" :: "IO" :: "CC" :: "AW" :: Nil)
    }

    "list United States, Brazil, Canada, Australia, Russia, France, Argentina, Germany, Colombia and Venezuela " +
      " when looking for the countries with most airports" in {
      val leastAirports = route(app, FakeRequest(GET, "/v1/countries?query=airports&order=desc")).get
      status(leastAirports) mustBe OK
      contentType(leastAirports) mustBe Some("application/json")
      val countriesAndCount = contentAsJson(leastAirports).as[Seq[CountryAndCountDTO]]
      countriesAndCount.map(_.iso) must contain theSameElementsAs (
        "US" :: "BR" :: "CA" :: "AU" :: "RU" :: "FR" :: "AR" :: "DE" :: "CO" :: "VE" :: Nil)
    }

    "list United States when looking for the country with most airports" in {
      val leastAirports = route(app, FakeRequest(GET, "/v1/countries?query=airports&order=desc")).get
      status(leastAirports) mustBe OK
      contentType(leastAirports) mustBe Some("application/json")
      val countriesAndCount = contentAsJson(leastAirports).as[Seq[CountryAndCountDTO]]
      countriesAndCount.head.iso must be("US")
      countriesAndCount.head.count must be(21501)
    }

    //  GET        /v1/countries/runways        controllers.CountriesController.countriesAndRunwayTypes()
    "list all countries and their runway types" in {
      val countriesRunwaysType = route(app, FakeRequest(GET, "/v1/countries/runways")).get
      status(countriesRunwaysType) mustBe OK
      contentType(countriesRunwaysType) mustBe Some("application/json")
      val countriesAndRunways = contentAsJson(countriesRunwaysType).as[Seq[CountrySurfaceTypesDTO]]
      countriesAndRunways.size must be (237)
      countriesAndRunways.find(x => x.name == "France").get.surfaceTypes.size must be (18)
    }

  }


  "RunwaysController" should {
    //  GET        /v1/runways                  controllers.RunwaysController.runwaysRankBy(query: String, order: String, count: Int ?= 10)
    "list 10 least used identifiers for runways with count" in {
      val leastUsedIds = route(app, FakeRequest(GET, "/v1/runways?query=le_ident&order=asc")).get
      status(leastUsedIds) mustBe OK
      contentType(leastUsedIds) mustBe Some("application/json")
      val idAndCount = contentAsJson(leastUsedIds).as[Seq[IdCountDTO]]
      idAndCount.map(x => (x.id, x.count)) must contain theSameElementsAs (("08U", 1) :: ("\"16R\"", 1) :: ("H02", 1) ::
        ("N80E", 1) :: ("04B", 1) :: ("110", 1) :: ("132", 1) :: ("13G", 1) :: ("01H", 1) :: ("H10", 1) :: Nil)
    }

    "list 10 most used identifiers for runways with count" in {
      val leastUsedIds = route(app, FakeRequest(GET, "/v1/runways?query=le_ident&order=desc")).get
      status(leastUsedIds) mustBe OK
      contentType(leastUsedIds) mustBe Some("application/json")
      val idAndCount = contentAsJson(leastUsedIds).as[Seq[IdCountDTO]]
      idAndCount.map(x => (x.id, x.count)) must contain theSameElementsAs (("H1", 5566) :: ("18", 3180) ::
        ("09", 2581) :: ("17", 2320) :: ("16", 1559) :: ("12", 1506) :: ("14", 1469) :: ("08", 1459) :: ("13", 1447) ::
        ("15", 1399) :: Nil)
    }

  }


}
