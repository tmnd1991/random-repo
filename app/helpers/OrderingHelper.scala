package helpers

/**
  * Created by tmnd on 15/03/17.
  */
object OrderingHelper {
  def stringToIntOrdering(s: String): Ordering[Int] = {
    s.toLowerCase() match {
      case "asc" => Ordering.Int
      case "desc" => Ordering.Int.reverse
      case _ => Ordering.Int
    }
  }

}
