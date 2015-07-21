package acl.acts

/** A message sent from a [[acl.CommunicatingActor `CommunicatingActor`]] to a collection of other such actors
  * indicating that a previously received [[acl.acts.Propose `Propose`]] message has been accepted.
  *
  * @param proposal is the previously received [acl.Propose `Propose`] message that has been accepted.
  * @tparam A is the type of action expression used to construct the content of the `proposal`.
  * @note The `AcceptProposal` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor.acceptProposal `acceptProposal`]] action.
  */
case class AcceptProposal[A](proposal: Propose[A]) extends CommunicativeAct
