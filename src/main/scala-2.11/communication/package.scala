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
import akka.actor.ActorRef
import communication.PromiseMaker
import contracts.commitments.Contract

import scala.collection.immutable


/** Provides classes for facilitating communication between economic actors.
  *
  * ==Overview==
  * A [[PromiseMaker]] communicates with other [[PromiseMaker]] actors by promising
  * contracts which are then either accepted or rejected by the receiving
  * [[PromiseMaker]] actors. The `communication` communication defines the [[PromiseMaker]] trait
  * as well as the high-level [[PromiseMaker]] communication language. Our
  * high-level [[PromiseMaker]] communication language is influenced by, but
  * not slave to, the [[http://www.fipa.org/ Foundation for Intelligent Physical Agents (FIPA)]]
  * compliant [[http://www.fipa.org/specs/fipa00037/SC00037J.pdf Agent Communication Language (ACL)]].
  */
package object communication {

  /** A promise made by some [[PromiseMaker]] to a collection of other
    * [[PromiseMaker]] actors to perform actions specified in the
    * [[contracts.commitments.Contract Contract]].
    *
    * @param sender the [[PromiseMaker]] making the proposal.
    * @param receiver a collection of [[PromiseMaker]] actors who are receiving the
    *                 [[Promise]] and therefore are potential counter parties to
    *                 the [[contracts.commitments.Contract Contract]].
    * @param contract a [[contracts.commitments.Contract Contract]] specifying
    *                 certain actions that the sender and receiver should perform.
    */
  case class Promise(sender: ActorRef, receiver: immutable.Set[ActorRef], contract: Contract)

  /** A message indicating that a [[PromiseMaker]] has broken a previously accepted
    * [[Promise]].
    *
    * @param sender the [[PromiseMaker]] breaking the existing [[contracts.commitments.Contract Contract]].
    * @param receiver the [[ContractActor]] representing the existing
    *                 [[contracts.commitments.Contract Contract]] who's terms are being breached.
    */
  case class PromiseBroken(sender: ActorRef, receiver: ActorRef)

  /** A message indicated that a previously submitted [[Promise]] has been accepted.
    *
    * @param sender the [[PromiseMaker]] accepting the previously received [[Promise]].
    * @param receiver a collection of [[PromiseMaker]] actors who are receiving the
    *                 [[Promise]] and therefore are potential counter parties to
    *                 the [[contracts.commitments.Contract Contract]].
    * @param promise the previously received [[Promise]] that is being accepted.

    */
  case class PromiseAccepted(sender: ActorRef, receiver: immutable.Set[ActorRef], promise: Promise)

  /** A message indicating that a previously submitted [[Promise]] has been rejected.
    *
    * @param sender the [[PromiseMaker]] rejecting the previously received [[Promise]].
    * @param receiver a collection of [[PromiseMaker]] actors who are receiving the
    *                 [[Promise]] and therefore are potential counter parties to
    *                 the [[contracts.commitments.Contract Contract]].
    * @param promise the previously received [[Promise]] that is being rejected.
    */
  case class PromiseRejected(sender: ActorRef, receiver: immutable.Set[ActorRef], promise: Promise)

}
