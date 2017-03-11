package model

import zamblauskas.csv.parser._

/**
  * Created by tmnd on 08/03/17.
  */
case class Airport(id: Long,
                   ident: String,
                   `type`: String,
                   name: String,
                   latitude_deg: Float,
                   longitude_deg: Float,
                   elevation_ft: Option[Float],
                   continent: String,
                   iso_country: String,
                   iso_region: String,
                   municipality: Option[String],
                   scheduled_service: Boolean,
                   gps_code: Option[String],
                   iata_code: Option[String],
                   local_code: Option[String],
                   home_link: Option[String],
                   wikipedia_link: Option[String],
                   keywords: List[String])
/*
object Airport {
  val fields = Array(
    "id",
    "ident",
    "type",
    "name",
    "latitude_deg",
    "longitude_deg",
    "elevation_ft",
    "continent",
    "iso_country",
    "iso_region",
    "municipality",
    "scheduled_service",
    "gps_code",
    "iata_code",
    "local_code",
    "home_link",
    "wikipedia_link",
    "keywords").zipWithIndex
}
*/
