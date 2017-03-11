package model

/**
  * Created by tmnd on 10/03/17.
  */
case class Runway(id: Long,
                  airport_ref: Long,
                  airport_ident: String,
                  length_ft: Option[Float],
                  width_ft: Option[Float],
                  surface: String,
                  lighted: Boolean,
                  closed: Boolean,
                  le_ident: String,
                  le_latitude_deg: Option[Float],
                  le_longitude_deg: Option[Float],
                  le_elevation_ft: Option[Float],
                  le_heading_degT: Option[Float],
                  le_displaced_threshold_ft: Option[Float],
                  he_ident: String,
                  he_latitude_deg: Option[Float],
                  he_longitude_deg: Option[Float],
                  he_elevation_ft: Option[Float],
                  he_heading_degT: Option[Float],
                  he_displaced_threshold_ft: Option[Float]) {

}
