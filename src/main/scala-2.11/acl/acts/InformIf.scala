package acl.acts

import acl.Beliefs

/** A message sent from a [[acl.CommunicatingActor `CommunicatingActor`]] to another
  * [[acl.CommunicatingActor `CommunicatingActor`]] informing it whether or not some proposition is true.
  *
  * @param proposition is a proposition whose truth value the `sender` has knowledge of which it intends to share
  *                    with the `receiver`.
  * @note The `InformIf` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor.informIf `informIf`]] action.
  */
case class InformIf(proposition: (Beliefs) => Boolean) extends CommunicativeAct
