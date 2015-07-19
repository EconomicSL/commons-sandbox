/*
Copyright 2015 David R. Pugh, Dan F. Tang, J. Doyne Farmer

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
specific language governing permissions and limitations under the License.
*/
package acl

import akka.actor.ActorRef

import scala.collection.immutable

sealed trait CommunicativeAct


/** A message sent from a [[acl.CommunicatingActor `CommunicatingActor`]] to a collection of other such actors
  * indicating that a previously received [[acl.Propose `Propose`]] message has been accepted.
  *
  * @param sender is the [[acl.CommunicatingActor `CommunicatingActor`]] accepting the `proposal`.
  * @param receiver is the collection of [[acl.CommunicatingActor `CommunicatingActor`]] that are being notified
  *                 that the `sender` has accepted the `proposal`.
  * @param proposal is the previously received [acl.Propose `Propose`] message that has been accepted.
  * @tparam A
  * @note The `AcceptProposal` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor.acceptProposal `acceptProposal`]] action.
  */
case class AcceptProposal[A](sender: ActorRef, receiver: Set[ActorRef], proposal: Propose[A]) extends CommunicativeAct


/** A message sent from a [[acl.CommunicatingActor `CommunicatingActor`]] to a collection of other such actors
  * indicating that a previously received [[acl.Request `Request`]] message to perform some action has been agreed.
  *
  * @param sender is the [[acl.CommunicatingActor `CommunicatingActor`]] agreeing to the `request`.
  * @param receiver is the collection of [[acl.CommunicatingActor `CommunicatingActor`]] that are being notified
  *                 that the `sender` has agreed to the `request`.
  * @param request is the previously received [acl.Request `Request`] message to perform some action that has been
  *                agreed.
  * @param precondition
  * @tparam A
  * @note The `Agree` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor.agree `agree`]] action.
  */
case class Agree[A](sender: ActorRef,
                    receiver: immutable.Set[ActorRef],
                    request: Request[A],
                    precondition: (Beliefs) => Boolean) extends CommunicativeAct


case class CallForProposal(sender: ActorRef, receiver: immutable.Set[ActorRef]) extends CommunicativeAct


/** A message sent from a [[acl.CommunicatingActor `CommunicatingActor`]] to a collection of other such actors
  * indicating that a previously received [[acl.Request `Request`]] message to perform some action should be cancelled.
  *
  * @param sender is the [[acl.CommunicatingActor `CommunicatingActor`]] cancelling the `request`.
  * @param receiver is the collection of [[acl.CommunicatingActor `CommunicatingActor`]] that are being notified
  *                 that the `sender` has cancelled the `request`.
  * @param request is the previously received [acl.Request `Request`] message to perform some action that has been
  *                cancelled.
  * @tparam A
  * @note The `Cancel` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor.cancel `cancel`]] action.
  */
case class Cancel[A](sender: ActorRef, receiver: immutable.Set[ActorRef], request: Request[A]) extends CommunicativeAct


/** A message sent from a [[acl.CommunicatingActor `CommunicatingActor`]] to a collection of other such actors
  * indicating that a proposition is true, where the receiving actors are known to, at a minimum, be uncertain about
  * the truth value of the proposition.
  *
  * @param sender is the [[acl.CommunicatingActor `CommunicatingActor`]] confirming the `proposition`.
  * @param receiver is the collection of actors that are being notified that the `CommunicatingActor` has confirmed
  *                 the `proposition`.
  * @param proposition is a proposition that the `CommunicatingActor` believes to be true, and intends that the
  *                    `receiver` also comes to believe to be true.
  * @note The `Confirm` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor.confirm `confirm`]] action.
  */
case class Confirm(sender: ActorRef,
                   receiver: immutable.Set[ActorRef],
                   proposition: (Beliefs) => Boolean) extends CommunicativeAct


/** A message sent from a [[acl.CommunicatingActor `CommunicatingActor`]] to a collection of other such actors
  * indicating that a proposition is false, where the receiving actors are known to, at a minimum, be uncertain about
  * the truth value of the proposition.
  *
  * @param sender is the [[acl.CommunicatingActor `CommunicatingActor`]] disconfirming the proposition.
  * @param receiver is the collection of actors that are being notified that the `CommunicatingActor` has disconfirmed
  *                 the `proposition`.
  * @param proposition is a proposition that the `CommunicatingActor` believes to be false, and intends that the
  *                    `receiver` also comes to believe to be false.
  * @note The `Disconfirm` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor.disconfirm `disconfirm`]] action.
  */
case class Disconfirm(sender: ActorRef,
                      receiver: immutable.Set[ActorRef],
                      proposition: (Beliefs) => Boolean) extends CommunicativeAct



case class Failure[A](sender: ActorRef,
                      receiver: immutable.Set[ActorRef],
                      content: A,
                      reason: (Beliefs) => Boolean) extends CommunicativeAct

case class Inform(sender: ActorRef,
                  receiver: immutable.Set[ActorRef],
                  proposition: (Beliefs) => Boolean) extends CommunicativeAct


case class InformIf(sender: ActorRef,
                    receiver: immutable.Set[ActorRef],
                    proposition: (Beliefs) => Boolean) extends CommunicativeAct

case class NotUnderstood[A](sender: ActorRef,
                            receiver: immutable.Set[ActorRef],
                            content: A,
                            reason: (Beliefs) => Boolean) extends CommunicativeAct


case class Propagate(sender: ActorRef,
                     receiver: immutable.Set[ActorRef],
                     content: CommunicativeAct,
                     descriptor: (ActorRef) => Boolean,
                     constraint: (Beliefs) => Boolean) extends CommunicativeAct


case class Propose[A](sender: ActorRef,
                      receiver: immutable.Set[ActorRef],
                      content: A,
                      precondition: (Beliefs) => Boolean,
                      inReplyTo: Option[Propose[A]]) extends CommunicativeAct


case class Proxy[A](sender: ActorRef,
                    receiver: immutable.Set[ActorRef],
                    content: A) extends CommunicativeAct


case class QueryIf() extends CommunicativeAct


/** A message sent from a [[acl.CommunicatingActor `CommunicatingActor`]] to a collection of other such actors
  * indicating that a previously received [[acl.Request `Request`]] message has been refused.
  *
  * @param sender is the [[acl.CommunicatingActor `CommunicatingActor`]] refusing the `request`.
  * @param receiver is the collection of [[acl.CommunicatingActor `CommunicatingActor`]] that is being notified
  *                 that the `sender` has refused the `request`.
  * @param request is the previously received [acl.Request `Request`] message that is being refused.
  * @param reason is a proposition denoting the reason that the `request` has been refused.
  * @tparam A
  * @note The `Refuse` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor.refuse `refuse`]] action.
  */
case class Refuse[A](sender: ActorRef,
                     receiver: immutable.Set[ActorRef],
                     request: Request[A],
                     reason: (Beliefs) => Boolean) extends CommunicativeAct


/** A message sent from a [[acl.CommunicatingActor `CommunicatingActor`]] to a collection of other such actors
  * indicating that a previously received [[acl.Propose `Propose`]] message has been rejected.
  *
  * @param sender is the [[acl.CommunicatingActor `CommunicatingActor`]] rejecting the `proposal`.
  * @param receiver is the collection of [[acl.CommunicatingActor `CommunicatingActor`]] that is being notified
  *                 that the `sender` has rejected the `proposal`.
  * @param proposal is the previously received [acl.Propose `Propose`] message that has been rejected.
  * @param reason is a proposition denoting the reason that the `proposal` has been rejected.
  * @tparam A
  * @note The `RejectProposal` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor.rejectProposal `rejectProposal`]] action.
  */
case class RejectProposal[A](sender: ActorRef,
                             receiver: immutable.Set[ActorRef],
                             proposal: Propose[A],
                             reason: (Beliefs) => Boolean) extends CommunicativeAct


/** A message sent from a [[acl.CommunicatingActor `CommunicatingActor`]] to a collection of other such actors
  * requesting them to perform some action.
  *
  * @param sender is the [[acl.CommunicatingActor `CommunicatingActor`]] requesting the `receiver` to perform certain
  *               actions as specified in the `content`.
  * @param receiver is the collection of [[acl.CommunicatingActor `CommunicatingActor`]] that are being requested to
  *                 perform action specified in the `content`.
  * @param content is an action expression defining the action(s) that the `sender` is requesting the `receiver` to
  *                perform.
  * @note The `Request` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor.request `request`]] action.
  */
case class Request[A](sender: ActorRef, receiver: immutable.Set[ActorRef], content: A) extends CommunicativeAct


/** A message sent from a [[acl.CommunicatingActor `CommunicatingActor`]] to a collection of other such actors
  * requesting them to perform some action when some given precondition becomes true.
  *
  * @param sender is the [[acl.CommunicatingActor `CommunicatingActor`]] requesting the `receiver` to perform certain
  *               action(s) as specified in the `content`.
  * @param receiver is the collection of [[acl.CommunicatingActor `CommunicatingActor`]] that are being requested to
  *                 perform action(s) specified in the `content`.
  * @param content is an action expression defining the action(s) that the `sender` is requesting the `receiver` to
  *                perform.
  * @param precondition is a proposition defining the precondition that should be satisfied in order for the
  *                     `receiver` to perform the action(s) specied in the `content`.
  * @tparam A
  * @note The `RequestWhen` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor.requestWhen `requestWhen`]] action.
  */
case class RequestWhen[A](sender: ActorRef,
                          receiver: immutable.Set[ActorRef],
                          content: A,
                          precondition: (Beliefs) => Boolean) extends CommunicativeAct


/** A message sent from a [[acl.CommunicatingActor `CommunicatingActor`]] to a collection of other such actors
  * requesting them to perform some action when some given precondition becomes true and thereafter each time the
  * precondition becomes true again.
  *
  * @param sender is the [[acl.CommunicatingActor `CommunicatingActor`]] requesting the `receiver` to perform certain
  *               action(s) as specified in the `content`.
  * @param receiver is the collection of [[acl.CommunicatingActor `CommunicatingActor`]] that are being requested to
  *                 perform action(s) specified in the `content`.
  * @param content is an action expression defining the action(s) that the `sender` is requesting the `receiver` to
  *                perform.
  * @param precondition is a proposition defining the precondition that should be satisfied in order for the
  *                     `receiver` to perform the action(s) specied in the `content`.
  * @tparam A
  * @note The `RequestWhenever` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor.requestWhenever `requestWhenever`]] action.
  */
case class RequestWhenever[A](sender: ActorRef,
                              receiver: immutable.Set[ActorRef],
                              content: A,
                              precondition: (Beliefs) => Boolean) extends CommunicativeAct