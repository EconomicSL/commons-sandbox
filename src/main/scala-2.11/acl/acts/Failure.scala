package acl.acts

import acl.Beliefs

/** A message sent from a [[acl.CommunicatingActor `CommunicatingActor`]] to a collection of other such actors
  * indicating that some action was attempted, but that the attempt failed.
  *
  * @param content  is an action expression defining the action(s) that the `sender` has failed to perform.
  * @tparam A is the type of action expression used to construct the `content` of the message.
  * @note The `Failure` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor.failure `failure`]] action.
  */
case class Failure[A](content: A, reason: (Beliefs) => Boolean) extends CommunicativeAct
