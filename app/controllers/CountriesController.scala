package controllers

import javax.inject.{Inject, Singleton}

import akka.actor.ActorSystem
import dao.CountriesDAO
import dto.Converter
import helpers.OrderingHelper
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, Controller}
import dto.format.implicits._
import scala.concurrent.ExecutionContext
import scala.language.reflectiveCalls

/**
  * Created by tmnd on 12/03/17.
  */
@Singleton
class CountriesController @Inject()(countryDao: CountriesDAO, actorSystem: ActorSystem)(implicit exec: ExecutionContext)
  extends Controller {

  private val queryOptions = new {
    val airports = "airports"
  }

  def countriesRankBy(query: String, order: String, howMany: Int): Action[AnyContent] = Action {
    query match {
      case queryOptions.airports =>
        val ordering = OrderingHelper.stringToIntOrdering(order)
        val countries = countryDao.topCountriesForAirportCount(howMany, ordering).map { case (c, count) =>
          Converter.apply(c, count)
        }
        Ok(Json.toJson(countries))
      case _ =>
        NotImplemented("")
    }
  }

  def countriesAndRunwayTypes(): Action[AnyContent] = Action {
    val runwayTypes = countryDao.runwayTypeForEachCountry()
    if (runwayTypes.isEmpty) {
      NotFound("")
    } else {
      Ok(Json.toJson(
        runwayTypes.map { case (country, surfaces) =>
          (Converter.apply(country,surfaces))
        })
      )
    }
  }
}
