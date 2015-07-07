/*
Copyright 2015 David R. Pugh, Dan F. Tang, J. Doyne Farmer

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package acl

import akka.actor.{Actor, ActorLogging, ActorRef}
import edsl.commitments.Commitment

import scala.collection.immutable


/** Trait defining behavior for an actor that trades [[edsl.commitments.Commitment Commitment]] objects. */
trait PromiseMakingActor {
  this: Actor with ActorLogging =>

  /** The act of accepting a previously submitted [[Promise]] to perform
    * actions specified in some [[edsl.commitments.Commitment Commitment]].
    *
    * @param promise a [[Promise]] containing a [[edsl.commitments.Commitment Commitment]] specifying certain actions
    *                that the `PromiseMakingActor` is to perform.
    * @note `acceptPromise` is a general-purpose acceptance of a [[Promise]]
    *       that was previously submitted (typically via a `makePromise` act).
    *       The `PromiseMakingActor` sending [[PromiseAccepted]] is informing the
    *       receiver that it intends to perform the actions as specified in
    *       the [[edsl.commitments.Commitment Commitment]].
    */
  def acceptPromise(promise: Promise): Unit = {
    promise.sender ! PromiseAccepted(self, promise.receiver, promise)
  }

  /** The action of submitting a [[Promise]] made by the `PromiseMakingActor` to a
    * collection of other `PromiseMakingActors` to perform actions specified in the
    * [[edsl.commitments.Commitment Commitment]].
    *
    * @param receiver a collection of `PromiseMakingActors` who are receiving the
    *                 [[Promise]] and therefore are potential counter parties to
    *                 the [[edsl.commitments.Commitment Commitment]].
    * @param contract a [[edsl.commitments.Commitment Commitment]] specifying certain actions that the various
    *                 parties should perform.
    * @note `makePromise` is a general-purpose act to make a [[Promise]] or
    *       respond to a previously submitted [[Promise]] (i.e., during a
    *       negotiation process) by promising to perform certain actions
    *       defined by the [[edsl.commitments.Commitment Commitment]].
    */
  def makePromise(receiver: immutable.Set[ActorRef], contract: Commitment): Unit = {
    receiver.foreach(r => r ! Promise(self, receiver, contract))
  }

  /** The action of rejecting a previously submitted [[Promise]] to perform
    * actions specified in some [[edsl.commitments.Commitment Commitment]].
    *
    * @param promise a [[Promise]] containing a [[edsl.commitments.Commitment Commitment]] specifying certain
    *                actions that the `PromiseMakingActor` is to perform.
    * @note `rejectPromise` is a general-purpose rejection to a [[Promise]]
    *       that was previously submitted (typically via a `makePromise` act).
    *       The `PromiseMakingActor` sending [[PromiseRejected]] is informing the
    *       receiver that it has no intention to perform the actions as
    *       specified in the promised [[edsl.commitments.Commitment Commitment]].
    */
  def rejectPromise(promise: Promise): Unit = {
    promise.sender ! PromiseRejected(self, promise.receiver, promise)
  }
}
