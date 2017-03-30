package dto

import model.Country

/**
  * Created by tmnd on 15/03/17.
  */
case class CountryAndCountDTO(name: String, iso: String, count: Int) extends DTO[(Country,Int)] {

}
