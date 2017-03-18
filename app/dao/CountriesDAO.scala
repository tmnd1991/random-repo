package dao

import com.google.inject.ImplementedBy
import model.{Airport, Country, Runway}

/**
  * Created by tmnd on 12/03/17.
  */

@ImplementedBy(classOf[InMemoryCountriesDAO])
trait CountriesDAO {
  def findAirportsWithRunways(q: String): Option[(Country, Map[Airport, Seq[Runway]])]

  def findCountry(q: String): Option[Country]

  def topCountriesForAirportCount(count: Int, order: Ordering[Int]): Seq[(Country, Int)]

  def runwayTypeForEachCountry(): Map[Country,Seq[String]]
}
