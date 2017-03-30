package dto

import model.Runway

/**
  * Created by tmnd on 12/03/17.
  */
case class RunwayDTO(airport_ident: String,
                     latitude_deg: Option[Float],
                     longitude_deg: Option[Float],
                     length_ft: Option[Float],
                     width_ft: Option[Float],
                     surface: String) extends DTO[Runway]
