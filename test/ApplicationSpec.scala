import dao.{CountriesDAO, InMemoryCountriesDAO}
import org.scalatestplus.play._
import parser.{CsvAirportParser, CsvCountryParser, CsvRunwayParser}
import play.api.test._
import play.api.test.Helpers._
import scala.language.reflectiveCalls
/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
class ApplicationSpec extends PlaySpec with OneAppPerTest {

  val fixture = new {
    import scala.concurrent.ExecutionContext.Implicits.global
    val dao: CountriesDAO = new InMemoryCountriesDAO(new CsvAirportParser(), new CsvRunwayParser(), new CsvCountryParser())
  }


  "Routes" should {

    "send 404 on a bad request" in  {
      route(app, FakeRequest(GET, "/boum")).map(status(_)) mustBe Some(NOT_FOUND)
    }

  }

  "HomeController" should {

    "render the index page" in {
      val home = route(app, FakeRequest(GET, "/")).get

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include ("Your new application is ready.")
    }

  }


//  GET        /v1/airports                 controllers.AirportsController.airportsForCountry(country: String)
  "AirportsController" should {
    "return all the airports of a given specific country" in {
      val italianAirports = route(app, FakeRequest(GET, "/v1/airports?country=Italy")).get
      status(italianAirports) mustBe OK
    }
  }

//  GET        /v1/countries                controllers.CountriesController.countriesRankBy(query: String, order: String, count: Int ?= 10)

//  GET        /v1/runways                  controllers.RunwaysController.runwaysRankBy(query: String, order: String, count: Int ?= 10)

//  GET        /v1/countries/runways        controllers.CountriesController.countriesAndRunwayTypes()


}
