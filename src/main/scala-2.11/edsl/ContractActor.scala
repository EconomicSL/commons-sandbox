/*
Copyright 2015 David R. Pugh, Dan F. Tang, J. Doyne Farmer

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License
is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
or implied. See the License for the specific language governing permissions and limitations under
the License.
*/
package edsl

import akka.actor.{PoisonPill, Actor, ActorLogging, ActorRef}
import edsl.commitments.{Give, Commitment}

import scala.collection.immutable


/** Base class for all `ContractActor` actors.
  *
  * @constructor Create a new `ContractActor` by specifying an `issuer`, an `owner`, and a
  *             `commitment`.
  * @param issuer A [[edsl.CounterpartyActor `CounterpartyActor`]] for whom the `ContractActor`
  *               represents a liability.
  * @param owner A [[edsl.CounterpartyActor `CounterpartyActor`]] for whom the `ContractActor`
  *              represents an asset.
  * @param commitment A [[edsl.commitments.Commitment `Commitment`]] made by the `issuer`
  *                   representing its obligations to the `owner`.
  */
class ContractActor(var issuer: ActorRef, var owner: ActorRef, commitment: Commitment) extends Actor
  with ActorLogging {

  import ContractActor._

  /** Breaks the `ContractActor`.
    *
    * @param guiltyParty The [[edsl.CounterpartyActor `CounterpartyActor`]] in breach of contract.
    * @note Breaking a `ContractActor` involves informing both the `issuer` and the `owner` using
    *       the [[edsl.ContractBroken `ContractBroken`]] message; removing the `ContractActor`
    *       from the [[edsl.balancesheets.BalanceSheetLike `BalanceSheet`]] of both the `issuer` and the
    *       `owner`; finally the `ContractActor` swallows the [[PoisonPill]] and terminates.
    */
  def breakContract(guiltyParty: ActorRef): Unit = {
    informCounterparties(counterparties, guiltyParty, self)
    removeFromBalanceSheets()
    self ! PoisonPill
  }

  /** Maps each [[edsl.CounterpartyActor `CounterpartyActor`]] actor to its respective
    * [[edsl.commitments.Commitment `Commitment`]] under the `ContractActor`.
    *
    * @note Because a `ContractActor` is created from the point-of-view of its `issuer`, the
    *       `issuer` is mapped to `commitment` while the `owner` is mapped to
    *       `Give(commitment)`.
    */
  def commitments: immutable.Map[ActorRef, Commitment] = {
    Map[ActorRef, Commitment](issuer -> commitment, owner -> Give(commitment))
  }

  /** The [[edsl.CounterpartyActor `CounterpartyActors`]] that are party to the `ContractActor`. */
  def counterparties: immutable.Set[ActorRef] = {
    Set(issuer, owner)
  }

  def receive: Receive = {
    case BreakContract(guiltyParty, _) => breakContract(guiltyParty)
  }

  /** Remove the `ContractActor` from all counterparty balance sheets. */
  def removeFromBalanceSheets(): Unit = {
    removeFromAssets(self, owner)
    removeFromLiabilities(self, issuer)
  }

}


object ContractActor {

  /** Identify the innocent counterparties to a broken contract.
    *
    * @param counterparties A collection of [[edsl.CounterpartyActor `CounterpartyActor`]] actors
    *                       to the [[edsl.ContractActor `ContractActor`]].
    * @param guiltyParty The [[edsl.CounterpartyActor `CounterpartyActor`]] actor who has broken
    *                    the [[edsl.ContractActor `ContractActor`]].
    * @return A collection of innocent [[edsl.CounterpartyActor `CounterpartyActor`]] actors to
    *         the [[edsl.ContractActor `ContractActor`]].
    */
  def identifyInnocentParties(counterparties: immutable.Set[ActorRef],
                              guiltyParty: ActorRef): immutable.Set[ActorRef] = {
    counterparties - guiltyParty
  }

  /** Inform counterparties to a contract that some guilty party has broken the contract.
    *
    * @param counterparties A collection of [[edsl.CounterpartyActor `CounterpartyActor`]] actors
    *                       to the [[edsl.ContractActor `ContractActor`]].
    * @param guiltyParty The [[edsl.CounterpartyActor `CounterpartyActor`]] actor who has broken
    *                    the [[edsl.ContractActor `ContractActor`]].
    * @param contract The [[edsl.ContractActor `ContractActor`]] that has been broken.
    */
  def informCounterparties(counterparties: immutable.Set[ActorRef],
                           guiltyParty: ActorRef,
                           contract: ActorRef): Unit = {
    val innocentParties = identifyInnocentParties(counterparties, guiltyParty)
    innocentParties.foreach(party => party ! ContractBroken(contract, innocentParties, guiltyParty))
  }

  /** Remove a contract from the asset side of a counterparty's balance sheet.
    *
    * @param contract A [[edsl.ContractActor `ContractActor`]].
    * @param counterparty A [[edsl.CounterpartyActor `CounterpartyActor`]] actor.
    */
  def removeFromAssets(contract: ActorRef, counterparty: ActorRef): Unit = {
    counterparty ! RemoveAsset(contract, counterparty)
  }

  /** Remove a contract from the liability side of a counterparty's balance sheet.
    *
    * @param contract A [[edsl.ContractActor `ContractActor`]] actor.
    * @param counterparty A [[edsl.CounterpartyActor `CounterpartyActor`]] actor.
    */
  def removeFromLiabilities(contract: ActorRef, counterparty: ActorRef): Unit = {
    counterparty ! RemoveLiability(contract, counterparty)
  }

}