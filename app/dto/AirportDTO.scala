package dto

import model.Airport

/**
  * Created by tmnd on 12/03/17.
  */
case class AirportDTO(name: String, iso_country: String, runways: Iterable[RunwayDTO]) extends DTO[Airport]
