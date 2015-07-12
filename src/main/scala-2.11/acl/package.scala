/*
Copyright 2015 David R. Pugh, Dan F. Tang, J. Doyne Farmer

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
specific language governing permissions and limitations under the License.
*/
import akka.actor.ActorRef
import acl.PromiseMakingActor
import edsl.commitments.Commitment


/** Provides classes defining a high-level economic actor communication language (ACL).
  *
  * ==Overview==
  * The `acl` package defines a high-level actor communication language (ACL) as well as the
  * [[PromiseMakingActor `PromiseMakingActor`]] behavioral trait that an actor must implement in order to
  * communicate using the ACL protocol.
  *
  * === Actor Communication Language ===
  * Our high-level [[PromiseMakingActor `PromiseMakingActor`]] actor communication language is influenced by, but not
  * slave to, the [[http://www.fipa.org/ Foundation for Intelligent Physical Agents (FIPA)]] compliant
  * [[http://www.fipa.org/specs/fipa00037/SC00037J.pdf Agent Communication Language (ACL)]].
  *
  * A [[PromiseMakingActor]] communicates with other [[PromiseMakingActor]] actors by making
  * [[acl.Promise Promise]] messages which are then either [[acl.PromiseAccepted accepted]] or
  * [[acl.PromiseRejected rejected]], or countered by the receiving [[PromiseMakingActor]] actors.
  *
  * A [[PromiseMakingActor `PromiseMakingActor`]] that wishes to cancel a previously accepted promise can do so by
  * sending the [[acl.CancelPromise `CancelPromise`]] message.
  *
  * ===`PromiseMakingActor`===
  *
  *
  */
package object acl {

  /** A message indicating that a previously accepted [[Promise `Promise`]] should be cancelled.
    *
    * @param sender The [[PromiseMakingActor `PromiseMakingActor`]] cancelling the `promise`.
    * @param receiver The [[PromiseMakingActor `PromiseMakingActor`]] who previously accepted the `promise`.
    * @param promise The previously accepted [[Promise `Promise`]] which should be cancelled.
    */
  case class CancelPromise(sender: ActorRef, receiver: ActorRef, promise: Promise)

  /** A `Promise` is a message from some [[PromiseMakingActor `PromiseMakingActor`]] to some other
    * [[PromiseMakingActor `PromiseMakingActor`]] which proposes that both the actors perform certain actions as
    * specified in the message `content`.
    *
    * @param sender The [[PromiseMakingActor `PromiseMakingActor`]] sending the `Promise`.
    * @param receiver The [[PromiseMakingActor `PromiseMakingActor`]] receiving the `Promise`.
    * @param content A [[edsl.commitments.Commitment `Commitment`]] specifying certain actions that the `sender` and
    *                `receiver` should perform given certain preconditions.
    * @param inReplyTo A previously submitted `Promise` to which the current `Promise` is in reply to.
    */
  case class Promise(sender: ActorRef, receiver: ActorRef, content: Commitment, inReplyTo: Option[Promise] = None)

  /** A message indicating that a previously received [[Promise `Promise`]] has been accepted.
    *
    * @param sender The [[PromiseMakingActor `PromiseMakingActor`]] accepting the [[Promise `Promise`]]
    * @param receiver The [[PromiseMakingActor `PromiseMakingActor`]] who originally sent the [[Promise `Promise`]].
    * @param inReplyTo The previously received `Promise` to which the `PromiseAccepted` message is in reply to.
    */
  case class PromiseAccepted(sender: ActorRef, receiver: ActorRef, inReplyTo: Promise)

  /** A message indicating that a previously accepted [[Promise `Promise`]] has been cancelled.
    *
    * @param sender The [[PromiseMakingActor `PromiseMakingActor`]] cancelling the `promise`.
    * @param receiver The [[PromiseMakingActor `PromiseMakingActor`]] who originally sent the `promise`.
    * @param inReplyTo The previously accepted `Promise` message which has been cancelled.
    */
  case class PromiseCancelled(sender: ActorRef, receiver: ActorRef, inReplyTo: Promise)

  /** A message indicating that a previously received [[Promise]] has been rejected.
    *
    * @param sender the [[PromiseMakingActor]] rejecting the previously received [[Promise]].
    * @param receiver a collection of [[PromiseMakingActor]] edsl who are receiving the
    *                 [[Promise]] and therefore are potential counter parties to
    *                 the [[edsl.commitments.Commitment Commitment]].
    * @param promise the previously received [[Promise]] that has been rejected.
    */
  case class PromiseRejected(sender: ActorRef, receiver: ActorRef, promise: Promise)

}
