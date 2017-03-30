package parser

import model.Country
import zamblauskas.csv.parser._
import parser.reads._

import scala.util.{Failure, Success, Try}

/**
  * Created by tmnd on 08/03/17.
  */

class CsvCountryParser extends CsvParser[Country] {
  def parseLine(s: String, h: String): Try[Country] = {
    val parsed = Parser.parse[Country](h + "\n" + s)
    parsed match {
      case Right(listResult) =>
        listResult.headOption match {
          case Some(country) => Success(country)
          case _ => Failure(new IllegalArgumentException("Not parsable line: " + s))
        }
      case Left(failure) => Failure(new IllegalArgumentException(failure.message + " at " + failure.line))
    }
  }
}
