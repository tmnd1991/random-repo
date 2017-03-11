package parser

import model.{Country, Runway}
import zamblauskas.csv.parser._
import parser.reads._

import scala.util.{Failure, Success, Try}

/**
  * Created by tmnd on 08/03/17.
  */

class CsvRunwayParser extends CsvParser[Runway] {
  def parseLine(s: String, h: String): Try[Runway] = {
    val parsed = Parser.parse[Runway](h + "\n" + s)
    parsed match {
      case Right(listResult) =>
        listResult.headOption match {
          case Some(runway) => Success(runway)
          case _ => Failure(new IllegalArgumentException("Not parsable line: " + s))
        }
      case Left(failure) => Failure(new IllegalArgumentException(failure.message + " at " + failure.line))
    }
  }
}
