package acl.acts

import acl.Beliefs

/** A message sent from a [[acl.CommunicatingActor `CommunicatingActor`]] to a collection of other such actors
  * indicating that a previously received [[acl.acts.Propose `Propose`]] message has been rejected.
  *
  * @param proposal is the previously received [acl.Propose `Propose`] message that has been rejected.
  * @param reason is a proposition denoting the reason that the `proposal` has been rejected.
  * @tparam A is the type of action expression used to construct the content of the `proposal`.
  * @note The `RejectProposal` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor.rejectProposal `rejectProposal`]] action.
  */
case class RejectProposal[A](proposal: Propose[A], reason: (Beliefs) => Boolean) extends CommunicativeAct
