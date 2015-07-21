package acl.acts

import acl.Beliefs

/** A message sent from a [[acl.CommunicatingActor `CommunicatingActor`]] to a collection of other such actors
  * indicating that a proposition is true, where the receiving actors are known to, at a minimum, be uncertain about
  * the truth value of the proposition.
  *
  * @param proposition is a proposition that the `CommunicatingActor` believes to be true, and intends that the
  *                    `receiver` also comes to believe to be true.
  * @note The `Confirm` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor.confirm `confirm`]] action.
  */
case class Confirm(proposition: (Beliefs) => Boolean) extends CommunicativeAct
