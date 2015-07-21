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
  * @param proposal is the previously received [acl.Propose `Propose`] message that has been accepted.
  * @tparam A is the type of action expression used to construct the content of the `proposal`.
  * @note The `AcceptProposal` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor.acceptProposal `acceptProposal`]] action.
  */
case class AcceptProposal[A](proposal: Propose[A]) extends CommunicativeAct


/** A message sent from a [[acl.CommunicatingActor `CommunicatingActor`]] to a collection of other such actors
  * indicating that a previously received [[acl.Request `Request`]] message to perform some action has been agreed.
  *
  * @param request is the previously received [acl.Request `Request`] message to perform some action that has been
  *                agreed.
  * @param precondition is a proposition defining the precondition that should be satisfied in order for the
  *                     `receiver` to perform the action(s) specified in the `request`.
  * @tparam A is the type of action expression used to construct the content of the `request`.
  * @note The `Agree` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor.agree `agree`]] action.
  */
case class Agree[A](request: Request[A], precondition: (Beliefs) => Boolean) extends CommunicativeAct


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


/** A message sent from a [[acl.CommunicatingActor `CommunicatingActor`]] to a collection of other such actors
  * indicating that a previously received [[acl.Request `Request`]] message to perform some action should be cancelled.
  *
  * @param request is the previously received [acl.Request `Request`] message to perform some action that has been
  *                cancelled.
  * @tparam A is the type of action expression used to construct the content of the `request`
  * @note The `Cancel` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor.cancel `cancel`]] action.
  */
case class Cancel[A](request: Request[A]) extends CommunicativeAct


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


/** A message sent from a [[acl.CommunicatingActor `CommunicatingActor`]] to a collection of other such actors
  * indicating that some action was attempted, but that the attempt failed.
  *
  * @param content  is an action expression defining the action(s) that the `sender` has failed to perform.
  * @tparam A is the type of action expression used to construct the `content` of the message.
  * @note The `Failure` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor.failure `failure`]] action.
  */
case class Failure[A](content: A, reason: (Beliefs) => Boolean) extends CommunicativeAct


/** A message sent from a [[acl.CommunicatingActor `CommunicatingActor`]] to a collection of other such actors
  * informing them that some proposition is true.
  *
  * @param proposition is a proposition that the `sender` believes to be true, and intends that the `receiver` also
  *                    comes to believe to be true.
  * @note The `Inform` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor.inform `inform`]] action.
  */
case class Inform(proposition: (Beliefs) => Boolean) extends CommunicativeAct


/** A message sent from a [[acl.CommunicatingActor `CommunicatingActor`]] to another
  * [[acl.CommunicatingActor `CommunicatingActor`]] informing it whether or not some proposition is true.
  *
  * @param proposition is a proposition whose truth value the `sender` has knowledge of which it intends to share
  *                    with the `receiver`.
  * @note The `InformIf` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor.informIf `informIf`]] action.
  */
case class InformIf(proposition: (Beliefs) => Boolean) extends CommunicativeAct


/** A message sent from a [[acl.CommunicatingActor `CommunicatingActor`]] to another
  * [[acl.CommunicatingActor `CommunicatingActor`]] informing it of object(s) which satisfy some descriptor.
  *
  * @param descriptor is a function describing some required characteristics of the object.
  * @tparam D is the type of objects described by the `descriptor`.
  * @note The `InformRef` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor.informRef `informRef`]] action.
  */
case class InformRef[D](descriptor: (D) => Boolean) extends CommunicativeAct


/** A message sent from a [[acl.CommunicatingActor `CommunicatingActor`]] to to another
  * [[acl.CommunicatingActor `CommunicatingActor`]] informing it that some previously received message was not
  * understood.
  *
  * @param message is the [[acl.CommunicativeAct `CommunicativeAct`]] that was not understood.
  * @note The `NotUnderstood` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor.notUnderstood `notUnderstood`]] action.
  */
case class NotUnderstood(message: CommunicativeAct, reason: (Beliefs) => Boolean) extends CommunicativeAct


/** A message sent from a [[acl.CommunicatingActor `CommunicatingActor`]] to to another
  * [[acl.CommunicatingActor `CommunicatingActor`]] in order to propagate some embedded message.
  *
  * @param message is the embedded [[acl.CommunicativeAct `CommunicativeAct`]] which is being propagated.
  * @param descriptor is a proposition denoting a collection of actors to whom the [[acl.Propagate `Propagate`]]
  *                   message should be sent by the `receiver`.
  * @param constraint is a proposition describing a termination condition for the propagation of the `message`.
  * @note The `Propagate` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor#propagate `propagate`]] action.
  */
case class Propagate(message: CommunicativeAct,
                     descriptor: (ActorRef) => Boolean,
                     constraint: (Beliefs) => Boolean) extends CommunicativeAct


case class Propose[A](content: A,
                      precondition: (Beliefs) => Boolean,
                      inReplyTo: Option[Propose[A]]) extends CommunicativeAct


/** A message sent from a [[acl.CommunicatingActor `CommunicatingActor`]] to to another
  * [[acl.CommunicatingActor `CommunicatingActor`]] in order to proxy some embedded message.
  *
  * @param message is the embedded [[acl.CommunicativeAct `CommunicativeAct`]] which is being proxied.
  * @param descriptor is a proposition denoting a collection of actors to whom the `Proxy` message should be sent by
  *                   the `receiver`.
  * @param constraint is a proposition describing a termination condition for the propagation of the `message`.
  * @note The `Proxy` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor#proxy `proxy`]] action.
  */
case class Proxy[D](message: CommunicativeAct,
                    descriptor: (D) => Boolean,
                    constraint: (Beliefs) => Boolean) extends CommunicativeAct


/** A message sent from a [[acl.CommunicatingActor `CommunicatingActor`]] to a collection of other such actors asking
  *  whether or not a given proposition is true.
  *
  * @param proposition is a proposition about which the `CommunicatingActor` is ignorant (i.e., has no knowledge of its
  *                    truth value).
  * @note the `QueryIf` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor.queryIf, `queryIf`]] method.
  */
case class QueryIf(proposition: (Beliefs) => Boolean) extends CommunicativeAct


/** A message sent from a [[acl.CommunicatingActor `CommunicatingActor`]] to a collection of other such actors asking
  * whether or not the receiving actors have references to objects matching a given descriptor.
  *
  * @param descriptor is a function describing some required characteristics of an object.
  * @tparam D is the type of object characterized by the `descriptor`.
  * @note the `QueryIf` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor.queryIf, `queryIf`]] method.
  */
case class QueryRef[D](descriptor: (D) => Boolean) extends CommunicativeAct


/** A message sent from a [[acl.CommunicatingActor `CommunicatingActor`]] to a collection of other such actors
  * indicating that a previously received [[acl.Request `Request`]] message has been refused.
  *
  * @param request is the previously received [acl.Request `Request`] message that is being refused.
  * @param reason is a proposition denoting the reason that the `request` has been refused.
  * @tparam A is the type of action expression used to construct the content of the `request`.
  * @note The `Refuse` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor.refuse `refuse`]] method.
  */
case class Refuse[A](request: Request[A], reason: (Beliefs) => Boolean) extends CommunicativeAct


/** A message sent from a [[acl.CommunicatingActor `CommunicatingActor`]] to a collection of other such actors
  * indicating that a previously received [[acl.Propose `Propose`]] message has been rejected.
  *
  * @param proposal is the previously received [acl.Propose `Propose`] message that has been rejected.
  * @param reason is a proposition denoting the reason that the `proposal` has been rejected.
  * @tparam A is the type of action expression used to construct the content of the `proposal`.
  * @note The `RejectProposal` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor.rejectProposal `rejectProposal`]] action.
  */
case class RejectProposal[A](proposal: Propose[A], reason: (Beliefs) => Boolean) extends CommunicativeAct


/** A message sent from a [[acl.CommunicatingActor `CommunicatingActor`]] to a collection of other such actors
  * requesting them to perform some action.
  *
  * @param request is an action expression defining the action(s) that the `sender` is requesting the `receiver` to
  *                perform.
  * @note The `Request` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor.request `request`]] action.
  */
case class Request[A](request: A) extends CommunicativeAct


/** A message sent from a [[acl.CommunicatingActor `CommunicatingActor`]] to a collection of other such actors
  * requesting them to perform some action when some given precondition becomes true.
  *
  * @param content is an action expression defining the action(s) that the `sender` is requesting the `receiver` to
  *                perform.
  * @param precondition is a proposition defining the precondition that should be satisfied in order for the
  *                     `receiver` to perform the action(s) specied in the `content`.
  * @tparam A is the type of action expression used to construct the content of the `request`.
  * @note The `RequestWhen` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor.requestWhen `requestWhen`]] action.
  */
case class RequestWhen[A](content: A, precondition: (Beliefs) => Boolean) extends CommunicativeAct


/** A message sent from a [[acl.CommunicatingActor `CommunicatingActor`]] to a collection of other such actors
  * requesting them to perform some action when some given precondition becomes true and thereafter each time the
  * precondition becomes true again.
  *
  * @param content is an action expression defining the action(s) that the `sender` is requesting the `receiver` to
  *                perform.
  * @param precondition is a proposition defining the precondition that should be satisfied in order for the
  *                     `receiver` to perform the action(s) specied in the `content`.
  * @tparam A is the type of action expression used to construct the content of the `request`.
  * @note The `RequestWhenever` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor.requestWhenever `requestWhenever`]] action.
  */
case class RequestWhenever[A](content: A, precondition: (Beliefs) => Boolean) extends CommunicativeAct