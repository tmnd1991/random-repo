package parser

import model.Airport
import zamblauskas.csv.parser._
import parser.reads._

import scala.util.{Failure, Success, Try}

/**
  * Created by tmnd on 08/03/17.
  */

class CsvAirportParser extends CsvParser[Airport] {
  def parseLine(s: String, h: String): Try[Airport] = {
    val parsed = Parser.parse[Airport](h + "\n" + s)
    parsed match {
      case Right(listResult) =>
        listResult.headOption match {
          case Some(airport) => Success(airport)
          case _ => Failure(new IllegalArgumentException("Not parsable line: " + s))
        }
      case Left(failure) => Failure(new IllegalArgumentException(failure.message + " at " + failure.line))
    }
  }
}
