package acl.acts

/** A message sent from a [[acl.CommunicatingActor `CommunicatingActor`]] to a collection of other such actors
  * requesting proposals that satisfy certain preconditions.
  *
  * @param content is an action expression defining the action(s) that the `sender` is requesting the `receiver` to
  *                submit a proposal to perform.
  * @param precondition is a proposition defining conditions that any submitted proposal must satisfy in order to be
  *                     accepted.
  * @tparam A is the type of action expression used to construct the `proposal`.
  */
case class CallForProposal[A](content: A, precondition: (Propose[A]) => Boolean) extends CommunicativeAct
