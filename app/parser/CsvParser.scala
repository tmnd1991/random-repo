package parser

import java.nio.file.Path

import model.Airport

import scala.util.{Failure, Success, Try}

/**
  * Created by tmnd on 10/03/17.
  */
trait CsvParser[T] {
  def parse(path: Path): Iterator[T] = {
    val lines = scala.io.Source.fromFile(path.toFile).getLines()
    val header = lines.next()
    lines.flatMap { l =>
      parseLine(l, header) match {
        case Success(result) => Some(result)
        case Failure(e) =>
          e.printStackTrace()
          None
      }
    }
  }

  def parseLine(s: String, h: String): Try[T]
}
