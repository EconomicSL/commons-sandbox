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
  * @param receiver is the collection of [[acl.CommunicatingActor `CommunicatingActor`]] that are being notified
  *                 that the `sender` has accepted the `proposal`.
  * @param proposal is the previously received [acl.Propose `Propose`] message that has been accepted.
  * @tparam A is the type of action expression used to construct the content of the `proposal`.
  * @note The `AcceptProposal` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor.acceptProposal `acceptProposal`]] action.
  */
case class AcceptProposal[A](receiver: immutable.Set[ActorRef], proposal: Propose[A]) extends CommunicativeAct


/** A message sent from a [[acl.CommunicatingActor `CommunicatingActor`]] to a collection of other such actors
  * indicating that a previously received [[acl.Request `Request`]] message to perform some action has been agreed.
  *
  * @param receiver is the collection of [[acl.CommunicatingActor `CommunicatingActor`]] that are being notified
  *                 that the `sender` has agreed to the `request`.
  * @param request is the previously received [acl.Request `Request`] message to perform some action that has been
  *                agreed.
  * @param precondition is a proposition defining the precondition that should be satisfied in order for the
  *                     `receiver` to perform the action(s) specified in the `request`.
  * @tparam A is the type of action expression used to construct the content of the `request`.
  * @note The `Agree` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor.agree `agree`]] action.
  */
case class Agree[A](receiver: immutable.Set[ActorRef],
                    request: Request[A],
                    precondition: (Beliefs) => Boolean) extends CommunicativeAct


/** A message sent from a [[acl.CommunicatingActor `CommunicatingActor`]] to a collection of other such actors
  * requesting proposals that satisfy certain preconditions.
  *
  * @param receiver is the collection of [[acl.CommunicatingActor `CommunicatingActor]] that are begin asked to
  *                 submit a proposal.
  * @param content is an action expression defining the action(s) that the `sender` is requesting the `receiver` to
  *                submit a proposal to perform.
  * @param precondition is a proposition defining conditions that any submitted proposal must satisfy in order to be
  *                     accepted.
  * @tparam A is the type of action expression used to construct the `proposal`.
  */
case class CallForProposal[A](receiver: immutable.Set[ActorRef],
                              content: A,
                              precondition: (Propose[A]) => Boolean) extends CommunicativeAct


/** A message sent from a [[acl.CommunicatingActor `CommunicatingActor`]] to a collection of other such actors
  * indicating that a previously received [[acl.Request `Request`]] message to perform some action should be cancelled.
  *
  * @param receiver is the collection of [[acl.CommunicatingActor `CommunicatingActor`]] that are being notified
  *                 that the `sender` has cancelled the `request`.
  * @param request is the previously received [acl.Request `Request`] message to perform some action that has been
  *                cancelled.
  * @tparam A is the type of action expression used to construct the content of the `request`
  * @note The `Cancel` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor.cancel `cancel`]] action.
  */
case class Cancel[A](receiver: immutable.Set[ActorRef], request: Request[A]) extends CommunicativeAct


/** A message sent from a [[acl.CommunicatingActor `CommunicatingActor`]] to a collection of other such actors
  * indicating that a proposition is true, where the receiving actors are known to, at a minimum, be uncertain about
  * the truth value of the proposition.
  *
  * @param receiver is the collection of actors that are being notified that the `CommunicatingActor` has confirmed
  *                 the `proposition`.
  * @param proposition is a proposition that the `CommunicatingActor` believes to be true, and intends that the
  *                    `receiver` also comes to believe to be true.
  * @note The `Confirm` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor.confirm `confirm`]] action.
  */
case class Confirm(receiver: immutable.Set[ActorRef], proposition: (Beliefs) => Boolean) extends CommunicativeAct


/** A message sent from a [[acl.CommunicatingActor `CommunicatingActor`]] to a collection of other such actors
  * indicating that a proposition is false, where the receiving actors are known to, at a minimum, be uncertain about
  * the truth value of the proposition.
  *
  * @param receiver is the collection of actors that are being notified that the `CommunicatingActor` has disconfirmed
  *                 the `proposition`.
  * @param proposition is a proposition that the `CommunicatingActor` believes to be false, and intends that the
  *                    `receiver` also comes to believe to be false.
  * @note The `Disconfirm` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor.disconfirm `disconfirm`]] action.
  */
case class Disconfirm(receiver: immutable.Set[ActorRef], proposition: (Beliefs) => Boolean) extends CommunicativeAct


/** A message sent from a [[acl.CommunicatingActor `CommunicatingActor`]] to a collection of other such actors
  * indicating that some action was attempted, but that the attempt failed.
  *
  * @param receiver is the collection of actors that are being notified that the `CommunicatingActor` has failed to
  *                 perform the actions specified in the `content`.
  * @param content  is an action expression defining the action(s) that the `sender` has failed to perform.
  * @tparam A is the type of action expression used to construct the `content` of the message.
  * @note The `Failure` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor.failure `failure`]] action.
  */
case class Failure[A](receiver: immutable.Set[ActorRef],
                      content: A,
                      reason: (Beliefs) => Boolean) extends CommunicativeAct


/** A message sent from a [[acl.CommunicatingActor `CommunicatingActor`]] to a collection of other such actors
  * informing them that some proposition is true.
  *
  * @param receiver is the collection of actors that are being notified that the `CommunicatingActor` that the
  *                 `proposition` is true.
  * @param proposition is a proposition that the `CommunicatingActor` believes to be true, and intends that the
  *                    `receiver` also comes to believe to be true.
  * @note The `Inform` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor.inform `inform`]] action.
  */
case class Inform(receiver: immutable.Set[ActorRef], proposition: (Beliefs) => Boolean) extends CommunicativeAct


/** A message sent from a [[acl.CommunicatingActor `CommunicatingActor`]] to a collection of other such actors
  * informing them whether or not some proposition is true.
  *
  * @param receiver is the collection of actors that are being notified that the `CommunicatingActor` has failed to
  *                 perform the actions specified in the `content`.
  * @param proposition is a proposition that the `CommunicatingActor` believes to be true, and intends that the
  *                    `receiver` also comes to believe to be true.
  * @note The `Inform` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor.inform `inform`]] action.
  */
case class InformIf(receiver: immutable.Set[ActorRef], proposition: (Beliefs) => Boolean) extends CommunicativeAct


/** A message sent from a [[acl.CommunicatingActor `CommunicatingActor`]] to a collection of other such actors
  * indicating that some action was attempted, but that the attempt failed.
  *
  * @param receiver is the collection of actors that are being notified that the `CommunicatingActor` has failed to
  *                 perform the actions specified in the `content`.
  * @param content  is an action expression defining the action(s) that the `sender` has failed to perform.
  * @tparam A is the type of action expression used to construct the `content` of the message.
  * @note The `Failure` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor.failure `failure`]] action.
  */
case class NotUnderstood[A](receiver: immutable.Set[ActorRef],
                            content: A,
                            reason: (Beliefs) => Boolean) extends CommunicativeAct


/** A message sent from a [[acl.CommunicatingActor `CommunicatingActor`]] to a collection of other such actors
  * indicating that some action was attempted, but that the attempt failed.
  *
  * @param receiver is the collection of actors that are being notified that the `CommunicatingActor` has failed to
  *                 perform the actions specified in the `content`.
  * @param content  is an action expression defining the action(s) that the `sender` has failed to perform.
  * @note The `Failure` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor.failure `failure`]] action.
  */
case class Propagate(receiver: immutable.Set[ActorRef],
                     content: CommunicativeAct,
                     descriptor: (ActorRef) => Boolean,
                     constraint: (Beliefs) => Boolean) extends CommunicativeAct


/** A message sent from a [[acl.CommunicatingActor `CommunicatingActor`]] to a collection of other such actors
  * indicating that some action was attempted, but that the attempt failed.
  *
  * @param receiver is the collection of actors that are being notified that the `CommunicatingActor` has failed to
  *                 perform the actions specified in the `content`.
  * @param content  is an action expression defining the action(s) that the `sender` has failed to perform.
  * @tparam A is the type of action expression used to construct the `content` of the message.
  * @note The `Failure` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor.failure `failure`]] action.
  */
case class Propose[A](receiver: immutable.Set[ActorRef],
                      content: A,
                      precondition: (Beliefs) => Boolean,
                      inReplyTo: Option[Propose[A]]) extends CommunicativeAct


/** A message sent from a [[acl.CommunicatingActor `CommunicatingActor`]] to a collection of other such actors
  * indicating that some action was attempted, but that the attempt failed.
  *
  * @param receiver is the collection of actors that are being notified that the `CommunicatingActor` has failed to
  *                 perform the actions specified in the `content`.
  * @param content  is an action expression defining the action(s) that the `sender` has failed to perform.
  * @tparam D is the type of action expression used to construct the `content` of the message.
  * @note The `Failure` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor.failure `failure`]] action.
  */
case class Proxy[D](receiver: immutable.Set[ActorRef],
                    content: CommunicativeAct,
                    descriptor: (D) => Boolean,
                    constraint: (Beliefs) => Boolean) extends CommunicativeAct


/** A message sent from a [[acl.CommunicatingActor `CommunicatingActor`]] to a collection of other such actors asking
  *  whether or not a given proposition is true.
  *
  * @param receiver is the collection of [[acl.CommunicatingActor `CommunicatingActor`]] receiving the query.
  * @param proposition is a proposition about which the `CommunicatingActor` is ignorant (i.e., has no knowledge of its
  *                    truth value).
  * @note the `QueryIf` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor.queryIf, `queryIf`]] method.
  */
case class QueryIf(receiver: immutable.Set[ActorRef],
                   proposition: (Beliefs) => Boolean) extends CommunicativeAct


/** A message sent from a [[acl.CommunicatingActor `CommunicatingActor`]] to a collection of other such actors asking
  * whether or not the receiving actors have references to objects matching a given descriptor.
  *
  * @param receiver is the collection of [[acl.CommunicatingActor `CommunicatingActor`]] receiving the query.
  * @param descriptor is a function describing some required characteristics of an object.
  * @tparam D is the type of object characterized by the `descriptor`.
  * @note the `QueryIf` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor.queryIf, `queryIf`]] method.
  */
case class QueryRef[D](receiver: immutable.Set[ActorRef],
                       descriptor: (D) => Boolean) extends CommunicativeAct


/** A message sent from a [[acl.CommunicatingActor `CommunicatingActor`]] to a collection of other such actors
  * indicating that a previously received [[acl.Request `Request`]] message has been refused.
  *
  * @param receiver is the collection of [[acl.CommunicatingActor `CommunicatingActor`]] that is being notified
  *                 that the `sender` has refused the `request`.
  * @param request is the previously received [acl.Request `Request`] message that is being refused.
  * @param reason is a proposition denoting the reason that the `request` has been refused.
  * @tparam A is the type of action expression used to construct the content of the `request`.
  * @note The `Refuse` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor.refuse `refuse`]] method.
  */
case class Refuse[A](receiver: immutable.Set[ActorRef],
                     request: Request[A],
                     reason: (Beliefs) => Boolean) extends CommunicativeAct


/** A message sent from a [[acl.CommunicatingActor `CommunicatingActor`]] to a collection of other such actors
  * indicating that a previously received [[acl.Propose `Propose`]] message has been rejected.
  *
  * @param receiver is the collection of [[acl.CommunicatingActor `CommunicatingActor`]] that is being notified
  *                 that the `sender` has rejected the `proposal`.
  * @param proposal is the previously received [acl.Propose `Propose`] message that has been rejected.
  * @param reason is a proposition denoting the reason that the `proposal` has been rejected.
  * @tparam A is the type of action expression used to construct the content of the `proposal`.
  * @note The `RejectProposal` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor.rejectProposal `rejectProposal`]] action.
  */
case class RejectProposal[A](receiver: immutable.Set[ActorRef],
                             proposal: Propose[A],
                             reason: (Beliefs) => Boolean) extends CommunicativeAct


/** A message sent from a [[acl.CommunicatingActor `CommunicatingActor`]] to a collection of other such actors
  * requesting them to perform some action.
  *
  * @param receiver is the collection of [[acl.CommunicatingActor `CommunicatingActor`]] that are being requested to
  *                 perform action specified in the `content`.
  * @param request is an action expression defining the action(s) that the `sender` is requesting the `receiver` to
  *                perform.
  * @note The `Request` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor.request `request`]] action.
  */
case class Request[A](receiver: immutable.Set[ActorRef], request: A) extends CommunicativeAct


/** A message sent from a [[acl.CommunicatingActor `CommunicatingActor`]] to a collection of other such actors
  * requesting them to perform some action when some given precondition becomes true.
  *
  * @param receiver is the collection of [[acl.CommunicatingActor `CommunicatingActor`]] that are being requested to
  *                 perform action(s) specified in the `content`.
  * @param content is an action expression defining the action(s) that the `sender` is requesting the `receiver` to
  *                perform.
  * @param precondition is a proposition defining the precondition that should be satisfied in order for the
  *                     `receiver` to perform the action(s) specied in the `content`.
  * @tparam A is the type of action expression used to construct the content of the `request`.
  * @note The `RequestWhen` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor.requestWhen `requestWhen`]] action.
  */
case class RequestWhen[A](receiver: immutable.Set[ActorRef],
                          content: A,
                          precondition: (Beliefs) => Boolean) extends CommunicativeAct


/** A message sent from a [[acl.CommunicatingActor `CommunicatingActor`]] to a collection of other such actors
  * requesting them to perform some action when some given precondition becomes true and thereafter each time the
  * precondition becomes true again.
  *
  * @param receiver is the collection of [[acl.CommunicatingActor `CommunicatingActor`]] that are being requested to
  *                 perform action(s) specified in the `content`.
  * @param content is an action expression defining the action(s) that the `sender` is requesting the `receiver` to
  *                perform.
  * @param precondition is a proposition defining the precondition that should be satisfied in order for the
  *                     `receiver` to perform the action(s) specied in the `content`.
  * @tparam A is the type of action expression used to construct the content of the `request`.
  * @note The `RequestWhenever` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor.requestWhenever `requestWhenever`]] action.
  */
case class RequestWhenever[A](receiver: immutable.Set[ActorRef],
                              content: A,
                              precondition: (Beliefs) => Boolean) extends CommunicativeAct