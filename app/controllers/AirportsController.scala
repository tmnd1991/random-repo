package controllers

import javax.inject.{Inject, Singleton}

import akka.actor.ActorSystem
import dao.CountriesDAO
import dto.{Converter, CountryAirportsDTO}
import model.Country
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, Controller}

import scala.concurrent.ExecutionContext

/**
  * Created by tmnd on 12/03/17.
  */
@Singleton
class AirportsController @Inject()(countryDao: CountriesDAO, actorSystem: ActorSystem)(implicit exec: ExecutionContext)
  extends Controller {

  def airportsForCountry(country: String): Action[AnyContent] = Action {
    import dto.format.implicits._
    countryDao.findAirportsWithRunways(country) match {
      case None =>
        NotFound("")
      case Some((country, airports)) =>
        Ok(Json.toJson(Converter.apply(country, airports)))
    }
  }

}
