package controllers

import javax.inject.{Inject, Singleton}

import akka.actor.ActorSystem
import dao.RunwaysDAO
import dto.Converter
import helpers.OrderingHelper
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, Controller}

import scala.concurrent.ExecutionContext
import scala.language.reflectiveCalls
import dto.format.implicits._

/**
  * Created by tmnd on 12/03/17.
  */
@Singleton
class RunwaysController @Inject()(runwaysDao: RunwaysDAO, actorSystem: ActorSystem)(implicit exec: ExecutionContext)
  extends Controller {

  private val queryOptions = new {
    val le_ident = "le_ident"
  }

  def runwaysRankBy(query: String, order: String, count: Int): Action[AnyContent] = Action {
    query.toLowerCase() match {
      case queryOptions.le_ident =>
        val ordering = OrderingHelper.stringToIntOrdering(order)
        val mostUsedIdentifiers = runwaysDao.mostUsedIdentifiers(count, ordering).map(x => Converter.apply(x._1, x._2))
        Ok(Json.toJson(mostUsedIdentifiers))
      case _ => NotImplemented("")
    }

  }
}
