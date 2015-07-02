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
import contracts.commitments.Contract

import scala.collection.immutable


/** Provides classes for facilitating communication between economic actors.
  *
  * ==Overview==
  * An `Actor` in our API that is `EconomicActorLike` communicates with other
  * `EconomicActorLike` actors by proposing contracts which are then either
  * accepted or rejected by the receiving actors. The `coms` package defines
  * the `EconomicActorLike` trait as well as the high-level `EconomicActorLike`
  * communication language. Our high-level `EconomicActorLike` communication
  * language is influenced by, but not slave to, the [[http://www.fipa.org/
  * Foundation for Intelligent Physical Agents (FIPA)]] compliant
  * [[http://www.fipa.org/specs/fipa00037/SC00037J.pdf
  * Agent Communication Language (ACL)]].
  */
package object coms {

  /** A proposal made by some actor to a collection of other actors to perform
    * actions specified in the contract.
    * @param sender the actor making the proposal.
    * @param receiver a collection of actors who are receiving the proposal and
    *                 therefore are counter party to the contract.
    * @param contract a contract specifying certain actions that the sender and
    *                 receivers should perform.
    */
  case class Proposal(sender: ActorRef, receiver: immutable.Set[ActorRef], contract: Contract)

  /** A proposal made by some actor to a collection of other actors to perform
    * actions specified in the contract.
    * @param sender the actor making the proposal.
    * @param receiver a collection of actors who are receiving the proposal and
    *                 therefore are counter party to the contract.
    * @param proposal a proposal containing a contract specifying certain
    *                 actions that the actor is to perform.
    */
  case class ProposalAccepted(sender: ActorRef, receiver: immutable.Set[ActorRef], proposal: Proposal)

  /** A proposal made by some actor to a collection of other actors to perform
    * actions specified in the contract.
    * @param sender the actor making the proposal.
    * @param receiver a collection of actors who are receiving the proposal and
    *                 therefore are counter party to the contract.
    * @param proposal a proposal containing a contract specifying certain
    *                 actions that the actor is to perform.
    */
  case class ProposalRejected(sender: ActorRef, receiver: immutable.Set[ActorRef], proposal: Proposal)

}
