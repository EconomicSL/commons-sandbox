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

import akka.actor.{ActorRef, Actor, ActorLogging}

import scala.collection.immutable


/** Trait defining the behavior of a `CommunicatingActor`.
  *
  */
trait CommunicatingActor {
  this: Actor with ActorLogging =>

  def beliefs: Beliefs

  /** The action of accepting a previously submitted [[acl.Propose `Propose`]] message to perform an action.
    *
    * @param receiver is the collection of actors that are being notified that the `CommunicatingActor` has accepted
    *                 the `proposal`.
    * @param proposal is the previously received [acl.Propose `Propose`] message that is being accepted.
    * @tparam A
    * @note `acceptProposal` is a general-purpose acceptance of a previously received [[acl.Propose `Propose`]]
    *       message. The `CommunicatingActor` sending the [[acl.AcceptProposal `AcceptProposal`]] message informs the
    *       `receiver` that it intends that the `receiver` will perform the according to the terms of the `proposal`.
    */
  def acceptProposal[A](receiver: Set[ActorRef], proposal: Propose[A]): Unit = {
    receiver.foreach(r => r! AcceptProposal(self, receiver, proposal))
  }

  /** The action of agreeing to perform some action, possibly in the future.
    *
    * @param receiver is the collection of actors that are being notified that the `CommunicatingActor` has agreed to
    *                 the `request`.
    * @param request is the previously received [acl.Request `Request`] that has been agreed.
    * @param precondition is a proposition that
    * @tparam A
    * @note `agree` is a general purpose agreement to a previously received [[acl.Request `Request`]] message to
    *       perform certain actions given that a precondition is satisfied. The `CommunicatingActor` sending the
    *       [[acl.Agree `Agree`]] message informs the `receiver` that it does intend to perform the actions as
    *       defined in `request`.
    */
  def agree[A](receiver: Set[ActorRef], request: Request[A], precondition: (Beliefs) => Boolean): Unit = {
    receiver.foreach(r => r ! Agree(self, receiver, request, precondition))
  }

  /** The action of calling for proposals to perform a given action.
    *
    * @param receiver
    * @param content An action expression denoting the action(s) to be done.
    * @tparam A
    */
  def callForProposal[A](receiver: immutable.Set[ActorRef], content: A, precondition: (Beliefs) => Boolean): Unit = {
    ???
  }

  /** The action of informing a collection of actors that the `CommunicatingActor` no longer has the intention
    * that the receiving actors perform the actions specified in a previously submitted [[acl.Request `Request`]].
    *
    * @param receiver is the collection of actors that are being notified that the `CommunicatingActor` has agreed to
    *                 the `request`.
    * @param request is the previously received [acl.Request `Request`] that is being cancelled.
    * @tparam A
    * @note The `cancel` act allows a `CommunicatingActor` to inform the `receiver` that it no longer intends that
    *       the `receiver` perform a previously requested action. This is not the same thing as a
    *       `CommunicatingActor` informing the `receiver` to stop performing an action.  In order for a
    *       `CommunicatingActor` to stop the `receiver` from performing an action it should send a
    *       [[acl.Request, `Request`]] message that the `receiver` stop performing that action.
    */
  def cancel[A](receiver: immutable.Set[ActorRef], request: Request[A]): Unit = {
    receiver.foreach(r => r ! Cancel(self, receiver, request))
  }

  /** The `CommunicatingActor` informs the `receiver` that some `proposition` is true, where the `receiver` is known to
    * either believe the `proposition` to be false (or to be uncertain about the truth value of the `proposition`).
    *
    * @param receiver is the collection of actors that are being notified that the `CommunicatingActor` has confirmed
    *                 the `proposition`.
    * @param proposition is a proposition that the `CommunicatingActor` believes to be true, and intends that the
    *                    `receiver` also comes to believe to be true.
    * @note The `confirm` act indicates that the `CommunicatingActor`:
    *
    *       1. believes that the `proposition` is true,
    *
    *       2. intends for the `receiver` to also believe that the `proposition` is true,
    *
    *       3. believes that the `receiver` believes that the `proposition` is false (or, at a minimum, believes that
    *       the `receiver` is uncertain about the truth value of the `proposition`).
    *
    *       Properties 1 and 2 require that the `CommunicatingActor` is "sincere." Property 3 determines whether the
    *       `CommunicatingActor` should use the `confirm` vs `inform` vs `disconfirm` action. Note that whether or
    *       not the `receiver` does indeed come to believe that the proposition is true will depend on the beliefs of
    *       the `receiver` concerning the sincerity and reliability of the `CommunicatingActor` sending the
    *       [[acl.Confirm]] message.
    */
  def confirm(receiver: immutable.Set[ActorRef], proposition: (Beliefs) => Boolean): Unit = {
    receiver.foreach(r => r ! Confirm(self, receiver, proposition))
  }

  /** The `CommunicatingActor` informs the `receiver` that some `proposition` is false, where the `receiver` is known to
    * either believe that the `proposition` is true (or be uncertain about its truth value).
    *
    * @param receiver is the collection of actors that are being notified that the `CommunicatingActor` has disconfirmed
    *                 the `proposition`.
    * @param proposition is a proposition that the `CommunicatingActor` believes to be false, intends that the
    *                    `receiver` also comes to believe to be false.
    * @note The `disconfirm` act indicates that the `CommunicatingActor`
    *
    *       1. believes that the `proposition` is false
    *
    *       2. intends for the `receiver` to also believe that the `proposition` is false
    *
    *       3. believes that the `receiver` believes that the `proposition` is true (or, at a minimum, believes that
    *       the `receiver` is uncertain about the truth value of the `proposition`).
    *
    *       Properties 1 and 2 require that the `CommunicatingActor` is "sincere." Property 3 determines whether the
    *       `CommunicatingActor` should use the `confirm` vs `inform` vs `disconfirm` action. Note that whether or
    *       not the `receiver` does indeed come to believe that the proposition is false will depend on the beliefs of
    *       the `receiver` concerning the sincerity and reliability of the `CommunicatingActor` sending the
    *       [[acl.Confirm]] message.
    */
  def disconfirm(receiver: immutable.Set[ActorRef], proposition: (Beliefs) => Boolean): Unit = {
    receiver.foreach(r => r ! Disconfirm(self, receiver, proposition))
  }

  /** The action of informing a collection of actors that the `CommunicatingActor` attempted some action, but that
    * the attempt failed.
    *
    * @param receiver is the collection of actors that are being notified that the `CommunicatingActor` has agreed to
    *                 the `request`.
    * @param content
    * @param reason is a proposition indicating the reason for the rejection.
    * @tparam A
    */
  def failure[A](receiver: immutable.Set[ActorRef], content: A, reason: (Beliefs) => Boolean): Unit = {
    receiver.foreach(r => r ! Failure(self, receiver, content, reason))
  }

  def inform(receiver: immutable.Set[ActorRef], proposition: (Beliefs) => Boolean): Unit = {
    receiver.foreach(r => r ! Inform(self, receiver, proposition))
  }

  /** A macro action for the `CommunicatingActor` to inform the `receiver` whether or not a `proposition` is true.
    *
    * @param receiver
    * @param proposition is a proposition that
    * @note The `informIf` act is an abbreviation for informing a `receiver` whether or not a `proposition` is
    *       believed. Note that the `CommunicatingActor` enacting `informIf` will actually perform a standard
    *       `inform` act.  The content of the [[acl.Inform `Inform`]] message will depend on `CommunicatingActor`
    *       beliefs. Specifically, if the `CommunicatingActor` believes the `proposition` is true, then it will
    *       `inform` the `receiver` that the `proposition` is true; if the `CommunicatingActor` believes the
    *       `proposition` is false, then it will `inform` the `receiver` that the `proposition` is false.
    *
    *       It may not be possible for the `CommunicatingActor` to perform the `informIf` act. For example, the
    *       `CommunicatingActor` may have no knowledge about the `proposition` in question; or will not permit the
    *       `receiver` to know the truth value of the `proposition` in order to avoid revealing private information.
    *
    *       Finally, while the `informIf` act can be planned or requested by a `CommunicatingActor` the `informIf` act
    *       can not be performed directly, but only upon receipt of an [[acl.InformIf `InformIf`]] message.
    */
  def informIf(receiver: immutable.Set[ActorRef], proposition: (Beliefs) => Boolean): Unit = {
    if (proposition(beliefs)) {
      receiver.foreach(r => r ! Inform(self, receiver, proposition))
    } else {
      receiver.foreach(r => r ! Inform(self, receiver, (b: Beliefs) => ! proposition(b)))
    }
  }

  /** Not sure this is necessary! */
  def informRef[C](receiver: immutable.Set[ActorRef], content: C): Unit = {
    ???
  }

  /** The `CommunicatingActor` informs the `receiver` that while it perceived the `receiver` to perform some action,
    * it did not understand the meaning behind the action. A particularly common use case is that the
    * `CommunicatingActor` tells the `receiver` that it did not understand the message that `receiver` sent to it.
    *
    * @param receiver
    * @param content
    * @param reason
    * @tparam A
    * @note
    */
  def notUnderstood[A](receiver: immutable.Set[ActorRef], content: A, reason: (Beliefs) => Boolean): Unit = {
    receiver.foreach(r => r ! NotUnderstood(self, receiver, content, reason))
  }

  /** The `CommunicatingActor` intends that the `receiver` treat the embedded `message` as if it was sent directly
    * from the `CommunicatingActor` to the `receiver`, and wants the `receiver` to identify the agents denoted by the
    * given `descriptor` and send the received [[acl.Propagate, `Propagate`]] message to them.
    *
    * @param receiver
    * @param message is the [[acl.CommunicativeAct `CommunicativeAct`]] which is being propagated.
    * @param descriptor is a proposition denoting a collection of actors to which the
    *                   [[acl.Propagate `Propagate`]] message should be sent.
    * @param constraint
    * @note
    *
    */
  def propagate(receiver: immutable.Set[ActorRef],
                message: CommunicativeAct,
                descriptor: (ActorRef) => Boolean,
                constraint: (Beliefs) => Boolean): Unit = {
    receiver.foreach(r => r ! Propagate(self, receiver, message, descriptor, constraint))
  }

  /** The action of submitting a proposal to perform a certain action given certain preconditions.
    *
    * @param receiver is a collection of actors receiving the [[acl.Propose `Propose`]] message.
    * @param content is an action expression representing the action that the `CommunicatingActor` is proposing to
    *                perform.
    * @param precondition is a proposition indicating the conditions for the action to be performed.
    * @param inReplyTo is a previously received [[acl.Propose `Propose`]] message to which the current
    *                  [[acl.Propose `Propose`]] message is a counter-proposal.
    * @tparam A
    */
  def propose[A](receiver: immutable.Set[ActorRef],
                 content: A,
                 precondition: (Beliefs) => Boolean,
                 inReplyTo: Option[Propose[A]] = None): Unit = {
    receiver.foreach(r => r ! Propose(self, receiver, content, precondition, inReplyTo))
  }

  def proxy(): Unit = {
    ???
  }

  /** Possible duplicate of informIf?? */
  def queryIf(receiver: immutable.Set[ActorRef]): Unit = {
    ???
  }

  def queryRef(): Unit = {
    ???
  }

  /** The action of one `CommunicatingActor` refusing to perform a request and explaining the reason for the
    * refusal.
    *
    * @param receiver is a collection of actors receiving the [[acl.Refuse `Refuse`]] message.
    * @param request is the [[acl.Request `Request]] that the `CommunicatingActor` can no longer perform.
    * @param reason is a proposition indicating the reason that the `request` is being refused.
    * @tparam A
    * @note The `refuse` act allows a `CommunicatingActor` to inform the `receiver` that it is no longer possible for
    *       it to perform a previously agreed `request`.
    */
  def refuse[A](receiver: immutable.Set[ActorRef], request: Request[A], reason: (Beliefs) => Boolean): Unit = {
    receiver.foreach(r => r ! Refuse(self, receiver, request, reason))
  }

  /** The action of rejecting a previously submitted proposal to perform an action.
    *
    * @param receiver is a collection of actors receiving the [[acl.RejectProposal `RejectProposal`]] message.
    * @param proposal is a previously received [[acl.Propose, `Propose`]] message that is being rejected.
    * @param reason is a proposition indicating the reason for the rejection.
    * @tparam A
    * @note `rejectProposal` is a general-purpose rejection of a previously received [[acl.Propose `Propose`]]
    *      message. The `CommunicatingActor` sending the [[acl.RejectProposal `RejectProposal`]] message informs the
    *      `receiver` that it has no intention that the `receiver` performs the given actions as defined in the
    *      `content`. The additional proposition `reason` indicates the reason that the `CommunicatingActor` rejected
    *      the
    */
  def rejectProposal[A](receiver: immutable.Set[ActorRef], proposal: Propose[A], reason: (Beliefs) => Boolean): Unit = {
    receiver.foreach(r => r! RejectProposal(self, receiver, proposal, reason))
  }

  /** The `CommunicatingActor` requests the receiver to perform some action.
    *
    * @param receiver is the collection of [[acl.CommunicatingActor `CommunicatingActor`]] that are being requested to
    *                 perform action(s) specified in the `content`.
    * @param content An action expression denoting the action(s) to be done.
    * @tparam A
    * @note The `CommunicatingActor` is requesting the `receiver` to perform some action. The `content` of the
    *       [[acl.Request `Request]] message is a description of the action to be performed in a language that the
    *       `receiver` understands.
    *
    *       An important use of the `request` act is to build composite conversations between `CommunicatingActor`,
    *       where the actions that are the object of the `request` act are themselves instances of
    *       [[CommunicativeAct `CommunicativeAct`]].
    */
  def request[A](receiver: immutable.Set[ActorRef], content: A): Unit = {
    receiver.foreach(r => r ! Request(self, receiver, content))
  }

  /** The `CommunicatingActor` requests the receiver to perform some action when some given precondition becomes true.
    *
    * @param receiver is the collection of [[acl.CommunicatingActor `CommunicatingActor`]] that are being requested to
    *                 perform action(s) specified in the `content`.
    * @param content An action expression denoting the action(s) to be done.
    * @param precondition A proposition indicating the conditions for the action to be performed.
    * @note The `requestWhen` act allows a `CommunicatingActor` to inform another actor that a certain action should
    *       be performed as soon as a given precondition, expressed as a proposition, becomes true.
    *
    *       The `CommunicatingActor` receiving a [[acl.RequestWhen `RequestWhen`]] message should either refuse to
    *       take on the commitment or should arrange that the action will be performed when the precondition becomes
    *       true. The commitment persists until...
    *
    *       - the precondition becomes true,
    *
    *       - the `CommunicatingActor` that sent the [[acl.RequestWhen `RequestWhen`]] cancels the request by sending
    *       a [[acl.Cancel `Cancel`]] message,
    *
    *       - the `CommunicatingActor` determines that it can no longer honour the commitment in which case it sends a
    *       [[acl.Refuse `Refuse`]] message.
    */
  def requestWhen[A](receiver: immutable.Set[ActorRef], content: A, precondition: (Beliefs) => Boolean): Unit = {
    receiver.foreach(r => r ! RequestWhen(self, receiver, content, precondition))
  }

  /** The `CommunicatingActor` requests the receiver to perform some action when some given precondition becomes true
    * and thereafter each time the proposition becomes true again.
    *
    * @param receiver is the collection of [[acl.CommunicatingActor `CommunicatingActor`]] that are being requested to
    *                 perform action(s) specified in the `content`.
    * @param content An action expression denoting the action(s) to be done.
    * @param precondition A proposition indicating the conditions for the action to be performed.
    * @tparam A
    * @note The `requestWhenever` act allows a `CommunicatingActor` to inform another actor that a certain action should
    *       be performed as soon as a given precondition, expressed as a proposition, becomes true, and that, after
    *       that, if the precondition should subsequently become false, the action will be repeated as soon as the
    *       precondition becomes true again.
    *
    *       The [[acl.RequestWhenever `RequestWhenever`]] message represents a persistent commitment to re-evaluate
    *       the given precondition and to take action when its value changes. The `CommunicatingActor` that sent the
    *       [[acl.RequestWhenever `RequestWhenever`]] message can cancel the commitment by sending a
    *       [[acl.Cancel `Cancel`]] message.
    */
  def requestWhenever[A](receiver: immutable.Set[ActorRef], content: A, precondition: (Beliefs) => Boolean): Unit = {
    receiver.foreach(r => r ! RequestWhenever(self, receiver, content, precondition))
  }

  /** Probably much better way to implement this in Akka already. */
  def subscribe(): Unit = {
    ???
  }

  def receive: Receive = {
    // inform the sender whether or not a proposition is true.
    case InformIf(sender, _, proposition) => informIf(immutable.Set(sender), proposition)
    case Propagate(sender, receiver, message, descriptor, constraint) =>
      ???
  }

}

