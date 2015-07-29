package acl.acts

import java.util.UUID

import acl.Beliefs


/** Base trait for representing responses to [[acl.acts.RequestActLike `RequestActLike`]] messages. */
sealed trait ResponseActLike extends CommunicativeAct


/** A message sent from some [[acl.CommunicatingActor `CommunicatingActor`]] (i.e., `sender`) to another
  * [[acl.CommunicatingActor `CommunicatingActor`]] (i.e., `receiver`) indicating that the `sender` has agreed to
  * perform some action(s) as specified in a [[acl.acts.Request `Request`]] message (ie., `request`) previously sent by
  * the `receiver`.
  *
  * @param conversationId is used to identify a sequence of [[acl.acts.CommunicativeAct `CommunicativeAct`]] messages
  *                       that together form a conversation.
  * @param request is the previously received `request` that has been agreed.
  * @param precondition defines a condition that should be satisfied in order for the `sender` to perform the action(s)
  *                     specified in the agreed `request`.
  * @tparam A is the type of action expression used to construct the content of the `request`.
  * @note The `Agree` message is sent by `sender` using the [[acl.CommunicatingActor.agree `agree`]] action.
  */
case class Agree[A](conversationId: UUID,
                    request: Request[A],
                    precondition: (Beliefs) => Boolean) extends ResponseActLike


/** A message sent from some [[acl.CommunicatingActor `CommunicatingActor`]] (i.e., `sender`) to another
  * [[acl.CommunicatingActor `CommunicatingActor`]] (i.e., `receiver`) indicating that the `sender` has refused a
  * previously received [[acl.acts.Request `Request`]] message (i.e., `request`) from the `receiver`.
  *
  * @param conversationId is used to identify a sequence of [[acl.acts.CommunicativeAct `CommunicativeAct`]] messages
  *                       that together form a conversation.
  * @param request is the previously received [acl.Request `Request`] message that is being refused.
  * @param reason is a proposition denoting the reason that the `request` has been refused.
  * @tparam A is the type of action expression used to construct the content of the `request`.
  * @note The `Refuse` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor.refuse `refuse`]] method.
  */
case class Refuse[A](conversationId: UUID, request: Request[A], reason: (Beliefs) => Boolean) extends ResponseActLike


