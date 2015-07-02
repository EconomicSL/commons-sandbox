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
import acl.PromiseMaker
import edsl.commitments.Contract

import scala.collection.immutable


/** Provides classes defining a high-level actor communication language (ACL).
  *
  * ==Overview==
  * A [[PromiseMaker]] communicates with other [[PromiseMaker]] edsl by promising
  * edsl which are then either accepted or rejected by the receiving
  * [[PromiseMaker]] edsl. The `acl` acl defines the [[PromiseMaker]] trait
  * as well as the high-level [[PromiseMaker]] acl language. Our
  * high-level [[PromiseMaker]] acl language is influenced by, but
  * not slave to, the [[http://www.fipa.org/ Foundation for Intelligent Physical Agents (FIPA)]]
  * compliant [[http://www.fipa.org/specs/fipa00037/SC00037J.pdf Agent Communication Language (ACL)]].
  */
package object acl {

  /** A message indicating that a [[Counterparty]] actor has broken an
    * existing [[edsl.commitments.Contract Contract]].
    *
    * @param sender the [[Counterparty]] actor breaking the existing
    *               [[edsl.commitments.Contract Contract]].
    * @param receiver the [[ContractLike]] actor representing the existing
    *                 [[edsl.commitments.Contract Contract]] who's terms
    *                 are being breached.
    */
  case class ContractBroken(sender: ActorRef, receiver: ActorRef)


  /** A promise made by some [[PromiseMaker]] to a collection of other
    * [[PromiseMaker]] edsl to perform actions specified in the
    * [[edsl.commitments.Contract Contract]].
    *
    * @param sender the [[PromiseMaker]] making the proposal.
    * @param receiver a collection of [[PromiseMaker]] edsl who are receiving the
    *                 [[Promise]] and therefore are potential counter parties to
    *                 the [[edsl.commitments.Contract Contract]].
    * @param contract a [[edsl.commitments.Contract Contract]] specifying
    *                 certain actions that the sender and receiver should perform.
    */
  case class Promise(sender: ActorRef, receiver: immutable.Set[ActorRef], contract: Contract)

  /** A message indicated that a previously submitted [[Promise]] has been accepted.
    *
    * @param sender the [[PromiseMaker]] accepting the previously received [[Promise]].
    * @param receiver a collection of [[PromiseMaker]] edsl who are receiving the
    *                 [[Promise]] and therefore are potential counter parties to
    *                 the [[edsl.commitments.Contract Contract]].
    * @param promise the previously received [[Promise]] that is being accepted.

    */
  case class PromiseAccepted(sender: ActorRef, receiver: immutable.Set[ActorRef], promise: Promise)

  /** A message indicating that a previously submitted [[Promise]] has been rejected.
    *
    * @param sender the [[PromiseMaker]] rejecting the previously received [[Promise]].
    * @param receiver a collection of [[PromiseMaker]] edsl who are receiving the
    *                 [[Promise]] and therefore are potential counter parties to
    *                 the [[edsl.commitments.Contract Contract]].
    * @param promise the previously received [[Promise]] that is being rejected.
    */
  case class PromiseRejected(sender: ActorRef, receiver: immutable.Set[ActorRef], promise: Promise)

}
