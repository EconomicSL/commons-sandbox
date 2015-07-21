package acl.acts

import acl.Beliefs

/** A message sent from a [[acl.CommunicatingActor `CommunicatingActor`]] to a collection of other such actors
  * indicating that a proposition is false, where the receiving actors are known to, at a minimum, be uncertain about
  * the truth value of the proposition.
  *
  * @param proposition is a proposition that the `CommunicatingActor` believes to be false, and intends that the
  *                    `receiver` also comes to believe to be false.
  * @note The `Disconfirm` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor.disconfirm `disconfirm`]] action.
  */
case class Disconfirm(proposition: (Beliefs) => Boolean) extends CommunicativeAct
