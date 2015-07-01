package actor.government

import akka.actor.{ActorLogging, Actor}
import coms.EconomicActorLike


/** Trait defining a monetary authority. */
trait MonetaryAuthorityLike extends EconomicActorLike {
  this: Actor with ActorLogging =>

}
