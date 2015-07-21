package acl.acts

import acl.Beliefs
import akka.actor.ActorRef

/** A message sent from a [[acl.CommunicatingActor `CommunicatingActor`]] to to another
  * [[acl.CommunicatingActor `CommunicatingActor`]] in order to propagate some embedded message.
  *
  * @param message is the embedded [[acl.acts.CommunicativeAct `CommunicativeAct`]] which is being propagated.
  * @param descriptor is a proposition denoting a collection of actors to whom the [[acl.acts.Propagate `Propagate`]]
  *                   message should be sent by the `receiver`.
  * @param constraint is a proposition describing a termination condition for the propagation of the `message`.
  * @note The `Propagate` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor#propagate `propagate`]] action.
  */
case class Propagate(message: CommunicativeAct,
                     descriptor: (ActorRef) => Boolean,
                     constraint: (Beliefs) => Boolean) extends CommunicativeAct
