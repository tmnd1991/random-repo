package dto.format
import dto._
import play.api.libs.json._
/**
  * Created by tmnd on 14/03/17.
  */
package object implicits {
  implicit val runwayFormats = Json.format[RunwayDTO]
  implicit val airportFormats = Json.format[AirportDTO]
  implicit val countryAirportsFormats = Json.format[CountryAirportsDTO]
  implicit val countryAndCountFormats = Json.format[CountryAndCountDTO]
  implicit val countrySurfaceTypesFormats = Json.format[CountrySurfaceTypesDTO]
  implicit val idCountFormats = Json.format[IdCountDTO]
}
