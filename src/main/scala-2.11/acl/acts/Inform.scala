package acl.acts

import acl.Beliefs

/** A message sent from a [[acl.CommunicatingActor `CommunicatingActor`]] to a collection of other such actors
  * informing them that some proposition is true.
  *
  * @param proposition is a proposition that the `sender` believes to be true, and intends that the `receiver` also
  *                    comes to believe to be true.
  * @note The `Inform` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor.inform `inform`]] action.
  */
case class Inform(proposition: (Beliefs) => Boolean) extends CommunicativeAct
