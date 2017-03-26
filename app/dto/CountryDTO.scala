package dto

import model.Country

/**
  * Created by tmnd on 15/03/17.
  */
case class CountryDTO(name: String, iso: String) extends DTO[Country]
