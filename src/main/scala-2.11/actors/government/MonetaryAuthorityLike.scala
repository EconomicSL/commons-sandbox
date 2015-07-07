package actors.government

import akka.actor.{ActorLogging, Actor}
import acl.PromiseMakingActor


/** Trait defining a monetary authority. */
trait MonetaryAuthorityLike extends PromiseMakingActor {
  this: Actor with ActorLogging =>

}
