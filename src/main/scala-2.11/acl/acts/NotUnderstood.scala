package acl.acts

import acl.Beliefs

/** A message sent from a [[acl.CommunicatingActor `CommunicatingActor`]] to to another
  * [[acl.CommunicatingActor `CommunicatingActor`]] informing it that some previously received message was not
  * understood.
  *
  * @param message is the [[acl.acts.CommunicativeAct `CommunicativeAct`]] that was not understood.
  * @note The `NotUnderstood` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor.notUnderstood `notUnderstood`]] action.
  */
case class NotUnderstood(message: CommunicativeAct, reason: (Beliefs) => Boolean) extends CommunicativeAct
