package dao

import com.google.inject.ImplementedBy

/**
  * Created by tmnd on 15/03/17.
  */
@ImplementedBy(classOf[InMemoryRunwaysDAO])
trait RunwaysDAO {
  def mostUsedIdentifiers(count: Int, order: Ordering[Int]): Seq[(String, Int)]
}
