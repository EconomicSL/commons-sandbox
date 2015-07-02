package actors.government

import akka.actor.{ActorLogging, Actor}
import acl.PromiseMaker


/** Trait defining a monetary authority. */
trait MonetaryAuthorityLike extends PromiseMaker {
  this: Actor with ActorLogging =>

}
