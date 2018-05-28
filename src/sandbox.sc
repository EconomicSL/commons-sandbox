import java.util.UUID

import acl.CommunicatingActor
import acl.acts.{InformRef, QueryRef}
import scala.collection.immutable
import scala.util.Random
case class House(listPrice: Double, numberRooms: Int)
val prng = new Random(42)
val houses = for (i <- 0 until 10) yield House(prng.nextDouble() * 100.0, prng.nextInt(10))
def housingPreferences(house: House): Boolean = {
  (house.listPrice <= 75.0) && (house.numberRooms >= 3)
}
def randomChoiceRule(alternatives: immutable.Iterable[House]): House = {
  prng.shuffle(alternatives).head
}
val query = QueryRef[House](UUID.randomUUID(), housingPreferences, Some(randomChoiceRule))
val feasibleAlternatives = houses.filter(query.descriptor)
feasibleAlternatives
val choice = query.selector match {
  case None =>
    feasibleAlternatives
  case Some(select) =>
    select(feasibleAlternatives)
}
val response = InformRef[House](query.conversationId, choice.asInstanceOf[House])
//class MarketActor extends CommunicatingActor with QueryHandler[House]