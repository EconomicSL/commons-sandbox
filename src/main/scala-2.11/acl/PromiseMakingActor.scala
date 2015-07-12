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

import akka.actor.{Actor, ActorLogging, ActorRef}
import edsl.commitments.Commitment

import scala.collection.mutable


/** Trait defining behavior for a `PromiseMakingActor`. */
trait PromiseMakingActor {
  this: Actor with ActorLogging =>

  /** Collection of [[acl.Promise `Promise`]] messages accepted by the `PromiseMakingActor`. */
  def acceptedPromises: mutable.Set[Promise]

  /** Collection of [[acl.Promise `Promise`]] messages sent by the `PromiseMakingActor` and accepted by some other 
    * `PromiseMakingActor`.
    */
  def outstandingPromises: mutable.Set[Promise]

  /** The act of informing another `PromiseMakingActor` that a previously received [[acl.Promise `Promise`]] has been
    * accepted.
    *
    * @param promise A [[acl.Promise `Promise`]] message.
    * @note `accept` is a general-purpose acceptance of a [[acl.Promise `Promise`]] message that was previously
    *       submitted (typically via a `make` act). The `PromiseMakingActor` sending
    *       [[acl.PromiseAccepted `PromiseAccepted`]] is informing the receiver that it intends to perform the actions
    *       as specified in the [[edsl.commitments.Commitment `Commitment`]].
    */
  def accept(promise: Promise): Unit = {
    acceptedPromises += promise
    promise.sender ! PromiseAccepted(self, promise.receiver, promise)
  }

  /** The act of informing another `PromiseMakingActor` that a previously accepted [[acl.Promise `Promise`]] has been
    * cancelled.
    * 
    * @param promise A [[acl.Promise `Promise`]] message.
    * @note The [[acl.PromiseCancelled `PromiseCancelled`]] message should only be sent in response to a
    *       [[acl.CancelPromise `CancelPromise`]] message.
    */
  def cancel(promise: Promise): Unit = {
    acceptedPromises -= promise
    promise.sender ! PromiseCancelled(self, promise.sender, promise)
  }

  /** The act of making a [[acl.Promise `Promise`]] to another `PromiseMakingActor`.
    *
    * @param commitment A [[edsl.commitments.Commitment `Commitment`]] specifying certain actions that the various
    *                   parties should perform.
    * @param receiver A `PromiseMakingActor` who is receiving the [[acl.Promise `Promise`]].
    * @note `make` is a general-purpose act to make a [[acl.Promise `Promise`]] or respond to a previously submitted
    *      [[acl.Promise `Promise`]] (i.e., during a negotiation process) by promising to perform certain actions
    *      defined by the [[edsl.commitments.Commitment `Commitment`]].
    */
  def make(commitment: Commitment, receiver: ActorRef): Unit = {
    receiver ! Promise(self, receiver, commitment)
  }

  /** The act of informing another `PromiseMakingActor` that a previously received [[acl.Promise `Promise`]] has been
    * rejected.
    *
    * @param promise A [[acl.Promise `Promise`]] message.
    * @note `rejectPromise` is a general-purpose rejection to a [[acl.Promise `Promise`]] that was previously submitted
    *       (typically via a `make` act). The `PromiseMakingActor` sending [[acl.PromiseRejected `PromiseRejected`]]
    *       is informing the receiver that it has no intention to perform the actions as specified in the underlying
    *       [[edsl.commitments.Commitment `Commitment`]].
    */
  def rejectPromise(promise: Promise): Unit = {
    promise.sender ! PromiseRejected(self, promise.receiver, promise)
  }

  def promiseMakingBehavior: Receive = {
    case CancelPromise(sender, receiver, promise) => cancel(promise)
    case PromiseAccepted(sender, receiver, promise) => outstandingPromises += promise
  }

}
