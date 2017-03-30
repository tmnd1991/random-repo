package dao

import java.nio.file.Paths
import javax.inject.{Inject, Singleton}

import model.{Airport, Country, Runway}
import org.apache.commons.lang3.StringUtils
import parser.{CsvAirportParser, CsvCountryParser, CsvRunwayParser}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

/**
  * Created by tmnd on 12/03/17.
  */
@Singleton
class InMemoryCountriesDAO @Inject()(csvAirportParser: CsvAirportParser, csvRunwayParser: CsvRunwayParser,
                                     csvCountryParser: CsvCountryParser)(implicit exec: ExecutionContext) extends CountriesDAO {

  private val _airports = Future(csvAirportParser.parse(Paths.get("resources/airports.csv")).toList)

  private val _countries = Future(csvCountryParser.parse(Paths.get("resources/countries.csv")).toList)

  private val _runways = Future(csvRunwayParser.parse(Paths.get("resources/runways.csv")).toList)

  private val _countriesAirports = Future {
    val countries = Await.result(_countries, Duration.Inf)
    val airports = Await.result(_airports, Duration.Inf).groupBy(_.iso_country)
    countries.map(c => (c, airports.get(c.code).getOrElse(List.empty))).toMap
  }
  private val _airportsRunways = Future {
    val airports = Await.result(_airports, Duration.Inf)
    val runways = Await.result(_runways, Duration.Inf).groupBy(_.airport_ref)
    airports.map(a => (a, runways.get(a.id).getOrElse(List.empty))).toMap
  }

  private def airports = Await.result(_airports, Duration.Inf)

  private def countries = Await.result(_countries, Duration.Inf)

  private def runways = Await.result(_runways, Duration.Inf)

  private def countriesAirports = Await.result(_countriesAirports, Duration.Inf)

  private def airportsRunways = Await.result(_airportsRunways, Duration.Inf)


  override def findAirportsWithRunways(q: String): Option[(Country, Map[Airport, Seq[Runway]])] = {
    if (!StringUtils.isBlank(q)) {
      val c = findCountry(q)
      c match {
        case Some(fc) =>
          Some((fc,
            countriesAirports(fc).
              map(a => (a, airportsRunways(a))).toMap))
        case _ => None
      }
    } else {
      None
    }
  }

  override def findCountry(q: String): Option[Country] = {
    if (StringUtils.isBlank(q)) {
      None
    } else {
      val lcq = q.toLowerCase()
      countries.find(c => c.code.toLowerCase() == lcq || c.name.toLowerCase() == lcq) match {
        case Some(country) => Some(country)
        case None => countries.find(c => c.code.toLowerCase().startsWith(lcq) || c.name.toLowerCase().startsWith(lcq))
      }
    }
  }

  override def topCountriesForAirportCount(count: Int, order: Ordering[Int]): Seq[(Country, Int)] = {
    countriesAirports.toList.map { case (c, a) => (c, a.size) }.sortBy(_._2)(order).take(count)
  }

  override def runwayTypeForEachCountry(): Map[Country, List[String]] = {
    countriesAirports.flatMap { case (country, airports) =>
      airports.flatMap { airport =>
        airportsRunways.get(airport) match {
          case None => None
          case Some(runways) => runways.map(r => StringUtils.capitalize(r.surface.toLowerCase)).distinct
        }
      }.distinct match {
        case Nil => None
        case surfaces => Some((country, surfaces))
      }
    }
  }
}
