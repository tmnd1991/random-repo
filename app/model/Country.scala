package model

/**
  * Created by tmnd on 10/03/17.
  */
case class Country(id: Long,
                   code: String,
                   name: String,
                   continent: String,
                   wikipedia_link: String,
                   keywords: List[String])
