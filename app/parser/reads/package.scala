package parser

import zamblauskas.csv.parser._

/**
  * Created by tmnd on 10/03/17.
  */
package object reads {

  implicit val listStringReads: Reads[List[String]] = new Reads[List[String]] {
    def read(column: Column): ReadResult[List[String]] = {
      column.value.trim match {
        case "" => ReadSuccess(List.empty)
        case s => ReadSuccess(s.split(",").toList)
      }
    }
  }

  implicit val longReads: Reads[Long] = new Reads[Long] {
    def read(column: Column): ReadResult[Long] = {
      try {
        ReadSuccess(column.value.toLong)
      } catch {
        case e: Throwable => ReadFailure(e.getMessage)
      }
    }
  }

  implicit val boolReads: Reads[Boolean] = new Reads[Boolean] {
    def read(column: Column): ReadResult[Boolean] = {
      try {
        ReadSuccess(column.value.toLowerCase match {
          case "yes" | "true" | "1" => true
          case "no" | "false" | "0" => false
        })
      }
      catch {
        case e: Throwable =>
          ReadFailure(e.getMessage)
      }
    }
  }
}
