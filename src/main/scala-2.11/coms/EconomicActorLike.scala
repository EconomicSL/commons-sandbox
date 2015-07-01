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
package coms

import java.util.UUID

import akka.actor.{Actor, ActorLogging, ActorRef}
import plumbing.contracts.commitments.Contract

import scala.collection.immutable


/** Trait representing a generic economic actor. */
trait EconomicActorLike {
  this: Actor with ActorLogging =>

  /** A unique identifier. */
  def id: UUID

  /** Balance sheet. */
  def balanceSheet: ActorRef

  /** The action of accepting a previously submitted proposal to perform actions
    * specified in some contract.
    * @param proposal a proposal containing a contract specifying certain
    *                 actions that the actor is to perform.
    * @note `acceptProposal` is a general-purpose acceptance of a proposal that
    *       was previously submitted (typically via a propose act). The agent
    *       sending `ProposalAccepted` is informing the receiver that it intends
    *       to perform the actions as specified in the proposal's contract.
    */
  def acceptProposal(proposal: Proposal): Unit = {
    proposal.sender ! ProposalAccepted(self, proposal.receiver, proposal)
  }

  /** The action of submitting a proposal made by the actor to a collection of
    * other actors to perform actions specified in the contract.
    * @param receiver a collection of actors who are receiving the proposal and
    *                 therefore are counter party to the contract.
    * @param contract a contract specifying certain actions that the various
    *                 parties should perform.
    * @note propose is a general-purpose act to make a proposal or respond to
    *       an existing proposal (possibly during a negotiation process) by
    *       proposing to perform certain actions defined by the contract.
    */
  def propose(receiver: immutable.Set[ActorRef], contract: Contract): Unit

  /** The action of rejecting a previously submitted proposal to perform actions
    * specified in some contract.
    * @param proposal a proposal containing a contract specifying certain
    *                 actions that the actor is to perform.
    * @note `rejectProposal` is a general-purpose rejection to a proposal that
    *       was previously submitted (typically via a propose act). The agent
    *       sending `ProposalRejected` is informing the receiver that it has no
    *       intention to perform the actions as specified in the proposal's
    *       contract.
    */
  def rejectProposal(proposal: Proposal): Unit = {
    proposal.sender ! ProposalRejected(self, proposal.receiver, proposal)
  }
}
