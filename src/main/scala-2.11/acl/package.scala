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
import acl.{Promise, PromiseMakingActor}
import edsl.{ContractActorLike, CounterpartyActor}
import edsl.commitments.Commitment

import scala.collection.immutable


/** Provides classes defining an economic actor as well as a high-level economic
  * actor communication language (ACL).
  *
  * ==Overview==
  * "To analyze how financial commitments affect the economy it is necessary to
  * look at economic units in terms of their cash flows. The cash-flow approach
  * looks at all units -- be they households, corporations, state and municipal
  * governments, or even national governments -- as if they were banks."
  *
  * The above quotation is taken from Hyman Minsky's magnum opus ''Stabilizing
  * an Unstable Economy''.  Following Minsky, and more recently Perry Mehrling,
  * we view every economic actor as an entity that is both receiving certain
  * cash flow events (i.e., receipts of various kinds) as well as generating
  * cash flow events (i.e., expenditures of various kinds) over time.
  *
  * The accumulation of cash flow events for a particular economic actor is
  * captured by its balance sheet. Most basic constraint that economic actors face in our framework is, to
  * again borrow from Minsky, the survival constraint: the inflow of cash flow
  * events must be at least as big as the outflow of generated cash flow
  * events.  By taking the "banking perspective" our primary concern is the
  * liquidity of economic actors; solvency of economic actors is a secondary
  * concern.
  *
  * Anytime an economic actor needs to make a payment it has only three
  * options:
  *
  *  1. Spend down hoards of money.
  *
  *  2. Liquidate accumulations of financial assets.
  *
  *  3. Increase accumulations of financial liabilities.
  *
  * Crucially both 2 and 3 depend on finding some other economic actor to take
  * the other side of the transaction. This may be easier said than done.  Only
  * option 1 is completely dependable.  Thus the import of money.
  *
  *
  * ===`PromiseMaker`===
  * The `acl` package defines the [[PromiseMakingActor]] trait as well as the
  * high-level [[PromiseMakingActor]] communication language.
  *
  * Our high-level [[PromiseMakingActor]] acl language is influenced by, but not slave
  * to, the [[http://www.fipa.org/ Foundation for Intelligent Physical Agents (FIPA)]]
  * compliant [[http://www.fipa.org/specs/fipa00037/SC00037J.pdf Agent Communication Language (ACL)]].
  *
  * A [[PromiseMakingActor]] communicates with other [[PromiseMakingActor]] actors by making
  * [[acl.Promise promises]] which are then either [[acl.PromiseAccepted accepted]]
  * or [[acl.PromiseRejected rejected]] by the receiving [[PromiseMakingActor]] actors.
  */
package object acl {

  /** A message indicating that a [[CounterpartyActor]] actor has broken an
    * existing [[edsl.commitments.Commitment Commitment]].
    *
    * @param sender the [[CounterpartyActor]] actor breaking the existing
    *               [[edsl.commitments.Commitment Commitment]].
    * @param receiver the [[ContractActorLike]] actor representing the existing
    *                 [[edsl.commitments.Commitment Commitment]] who's terms
    *                 are being breached.
    */
  case class ContractBroken(sender: ActorRef, receiver: ActorRef)


  /** A promise made by some [[PromiseMakingActor]] to a collection of other
    * [[PromiseMakingActor]] edsl to perform actions specified in the
    * [[edsl.commitments.Commitment Commitment]].
    *
    * @param sender the [[PromiseMakingActor]] making the proposal.
    * @param receiver a collection of [[PromiseMakingActor]] edsl who are receiving the
    *                 [[Promise]] and therefore are potential counter parties to
    *                 the [[edsl.commitments.Commitment Commitment]].
    * @param contract a [[edsl.commitments.Commitment Commitment]] specifying
    *                 certain actions that the sender and receiver should perform.
    */
  case class Promise(sender: ActorRef, receiver: immutable.Set[ActorRef], contract: Commitment)

  /** A message indicated that a previously submitted [[Promise]] has been accepted.
    *
    * @param sender the [[PromiseMakingActor]] accepting the previously received [[Promise]].
    * @param receiver a collection of [[PromiseMakingActor]] edsl who are receiving the
    *                 [[Promise]] and therefore are potential counter parties to
    *                 the [[edsl.commitments.Commitment Commitment]].
    * @param promise the previously received [[Promise]] that is being accepted.

    */
  case class PromiseAccepted(sender: ActorRef, receiver: immutable.Set[ActorRef], promise: Promise)

  /** A message indicating that a previously submitted [[Promise]] has been rejected.
    *
    * @param sender the [[PromiseMakingActor]] rejecting the previously received [[Promise]].
    * @param receiver a collection of [[PromiseMakingActor]] edsl who are receiving the
    *                 [[Promise]] and therefore are potential counter parties to
    *                 the [[edsl.commitments.Commitment Commitment]].
    * @param promise the previously received [[Promise]] that is being rejected.
    */
  case class PromiseRejected(sender: ActorRef, receiver: immutable.Set[ActorRef], promise: Promise)

}
