import acl.PromiseMakingActor
import akka.actor.ActorRef
import edsl.ContractActorLike

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

/** Provides classes for modeling commercial contracts.
  *
  * ==Overview==
  *
  * ===`Counterparty`===
  * Once a [[acl.Promise Promise]] has been accepted by all relevant [[PromiseMakingActor]] actors
  * the [[acl.Promise Promise]] can be used to construct a [[ContractActorLike]] actor that
  * serves as a "channel" of communication between the various
  * [[edsl.CounterpartyActor CounterParty]] actors for the duration of the underlying
  * [[edsl.commitments.Commitment Commitment]].
  *
  * A [[edsl.CounterpartyActor Counterparty]] actor should...
  *
  *  - be able to communicate directly with a [[ContractActorLike]] actor.
  *
  *  - be able to communicate with any relevant [[edsl.CounterpartyActor Counterparty]]
  *  actor indirectly using the [[ContractActorLike]] actor as channel.
  *
  *  - have a balance sheet.
  *
  *  - be able to add (remove) assets and liabilities from its balance sheet.
  *
  * ===`ContractLike`===
  * A [[ContractActorLike]] actor should have...
  *
  *  - an issuer: the issuer is the [[edsl.CounterpartyActor Counterparty]] actor for
  *  which the [[edsl.commitments.Commitment Commitment]] represents a liability.
  *
  *  - an immutable collection of owners: the owners are for
  *  which the contract represents an asset.
  *
  * ===`BalanceSheetLike`===

  * Instead of defining a fixed catalogue of contracts our approach is to follow...
  *
  *   - [[https://lexifi.com/files/resources/MLFiPaper.pdf Peyton-Jones et al (2000)]]
  *   - [[http://www.itu.dk/~elsborg/sttt06.pdf Andersen et al (2006)]]
  *
  * ...and instead define an [[https://en.wikipedia.org/wiki/Domain-specific_language
  * embedded domain specific language (EDSL)]]. Our EDSL consists of a small set of
  * atomic contracts, called [[edsl.commitments commitments]], that we use as
  * "building blocks" to construct ever more complex contracts.
  *
  * ===Composable Commercial Contracts===
  * Following [[http://www.itu.dk/~elsborg/sttt06.pdf Andersen et al (2006)]]
  * we focus on the following basic ''contract patterns'' for composing
  * commercial contracts from sub-contracts:
  *
  *  - A ''commitment'' stipulates the transfer of a resource or a set of
  *  resources between two parties; a commitment represents an ''atomic''
  *  `Commitment`.
  *
  *  - A `Commitment` may require ''sequential'' execution of its sub-contracts.
  *
  *  - A `Commitment` may require ''concurrent'' execution of its sub-contracts,
  *  i.e., execution of all sub-contracts, where individual commitments are
  *  inter-leavened in any order.
  *
  *  - A `Commitment` may require execution of one of a number of ''alternative''
  *  sub-contracts.
  *
  *  - A `Commitment` may require ''repeated'' execution of a sub-contract.
  *
  * ==Overview==
  *
  * ===Requirements===
  * Current list of requirements for edsl is as follows...
  *
  *  1. Each contract has a collection of commitments.
  *
  *  2. Each contract must have both a face value (objective) and a present value (subjective).
  *
  * ===Composable Commercial Contracts===
  */
package object edsl {

  /** Add a [[ContractActorLike]] to a [[CounterpartyActor]] actor's [[BalanceSheetLike]] as an asset. */
  case class AddAsset(contract: ActorRef)

  /** Remove a [[ContractActorLike]] asset from a [[CounterpartyActor]] actor's [[BalanceSheetLike]]. */
  case class RemoveAsset(contract: ActorRef)

  /** Add a [[ContractActorLike]] to a [[CounterpartyActor]] actor's [[BalanceSheetLike]] as a liability. */
  case class AddLiability(contract: ActorRef)

  /** Remove a [[ContractActorLike]] liability from a [[CounterpartyActor]] actor's [[BalanceSheetLike]]. */
  case class RemoveLiability(contract: ActorRef)

}

