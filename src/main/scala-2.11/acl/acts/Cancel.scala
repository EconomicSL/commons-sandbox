package acl.acts

/** A message sent from a [[acl.CommunicatingActor `CommunicatingActor`]] to a collection of other such actors
  * indicating that a previously received [[acl.acts.Request `Request`]] message to perform some action should be
  * cancelled.
  *
  * @param request is the previously received [acl.Request `Request`] message to perform some action that has been
  *                cancelled.
  * @tparam A is the type of action expression used to construct the content of the `request`
  * @note The `Cancel` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor.cancel `cancel`]] action.
  */
case class Cancel[A](request: Request[A]) extends CommunicativeAct
