package acl.acts

import acl.Beliefs

/** A message sent from a [[acl.CommunicatingActor `CommunicatingActor`]] to to another
  * [[acl.CommunicatingActor `CommunicatingActor`]] in order to proxy some embedded message.
  *
  * @param message is the embedded [[acl.acts.CommunicativeAct `CommunicativeAct`]] which is being proxied.
  * @param descriptor is a proposition denoting a collection of actors to whom the `Proxy` message should be sent by
  *                   the `receiver`.
  * @param constraint is a proposition describing a termination condition for the propagation of the `message`.
  * @note The `Proxy` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor#proxy `proxy`]] action.
  */
case class Proxy[D](message: CommunicativeAct,
                    descriptor: (D) => Boolean,
                    constraint: (Beliefs) => Boolean) extends CommunicativeAct
