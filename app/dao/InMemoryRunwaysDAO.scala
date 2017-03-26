package dao

import java.nio.file.Paths
import javax.inject.Inject

import parser.CsvRunwayParser

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

/**
  * Created by tmnd on 15/03/17.
  */
class InMemoryRunwaysDAO @Inject()(csvRunwayParser: CsvRunwayParser)(implicit exec: ExecutionContext) extends RunwaysDAO {

  private val _runways = Future(csvRunwayParser.parse(Paths.get("resources/runways.csv")).toList)

  private def runways = Await.result(_runways, Duration.Inf)

  override def mostUsedIdentifiers(count: Int, order: Ordering[Int]): Seq[(String, Int)] = {
    runways.foldLeft(Map.empty[String, Int]) { (z, r) =>
      val ident = r.le_ident
      z.get(ident) match {
        case None =>
          z + (ident -> 1)
        case Some(count) =>
          z + (ident -> (count + 1))
      }
    }.toList.sortBy(_._2)(order).take(count)
  }
}
