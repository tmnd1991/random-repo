package dto

import model.{Airport, Country, Runway}

/**
  * Created by tmnd on 14/03/17.
  */
object Converter {
  def apply(country: Country, surfaces: Seq[String]): CountrySurfaceTypesDTO = {
    CountrySurfaceTypesDTO(country.name, country.code, surfaces)
  }

  def apply(airport: Airport, runways: Iterable[Runway]): AirportDTO = {
    AirportDTO(name = airport.name, iso_country = airport.iso_country, runways.map(Converter.apply))
  }

  def apply(country: Country): CountryDTO = {
    CountryDTO(country.name, country.code)
  }

  def apply(country: Country, count: Int): CountryAndCountDTO = {
    CountryAndCountDTO(country.name, country.code, count)
  }

  def apply(runway: Runway): RunwayDTO = {
    RunwayDTO(runway.airport_ident, runway.length_ft, runway.width_ft, runway.surface)
  }

  def apply(country: Country, airports: Map[Airport, Iterable[Runway]]): CountryAirportsDTO = {
    CountryAirportsDTO(country.name, airports.map { case (airport, runways) => Converter(airport, runways) })
  }

  def apply(id: String, count: Int): IdCountDTO = {
    IdCountDTO(id, count)
  }
}
