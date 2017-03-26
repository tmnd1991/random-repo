package dto

import model.Country

/**
  * Created by tmnd on 15/03/17.
  */
case class CountrySurfaceTypesDTO(name: String, iso: String, surfaceTypes: Seq[String]) extends DTO[(Country,String)]
