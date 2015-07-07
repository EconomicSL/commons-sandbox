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
package edsl

import acl.ContractBroken
import akka.actor.{Actor, ActorLogging, ActorRef}
import edsl.commitments.{Give, And, Contract}


/** Trait defining behavior for an actor that is party to some
  * [[edsl.commitments.Contract Contract]].
  *
  * `Counterparty` actors communicate with other `Counterparty` actors using
  * [[ContractLike ContractLike]] actors as "channels."
  */
trait Counterparty {
  this: Actor with ActorLogging =>

  /** An actor's balance sheet. */
  def balanceSheet: BalanceSheetLike

  /** The act of breaking the terms of some existing [[edsl.commitments.Contract Contract]].
    *
    * @param receiver A [[ContractLike]] actor whose underlying
    *                 [[edsl.commitments.Contract Contract]] the `Counterparty`
    *                 actor wishes to break.
    * @note When a `Counterparty` actor breaks the terms of some existing
    *       [[edsl.commitments.Contract Contract]] it becomes in "breach
    *       of contract." The `Counterparty` actor sending [[ContractBroken]]
    *       is informing the receiver that it no longer has the intention of
    *       performing the actions specified in the receiver's underlying
    *       [[edsl.commitments.Contract Contract]].
    */
  def breakContract(receiver: ActorRef): Unit = {
    receiver ! ContractBroken(self, receiver)
  }

  /** Compute the value of the `Counterparty` actor's stock of assets.
    *
    * @param f A function mapping a [[ContractLike]] actor to a
    *          [[edsl.commitments.Contract Contract]] representing the value
    *          of the [[ContractLike]] actor's underlying
    *          [[edsl.commitments.Contract Contract]] to the `Counterparty` actor.
    * @return The total value of the `Counterparty` actor's stock of assets.
    */
  def valueAssets(f: ActorRef => Contract): Contract = {
    balanceSheet.assets.map(f).reduce((contract1, contract2) => And(contract1, contract2))
  }

  /** Compute the value of the `Counterparty` actor's equity.
    *
    * @param f A function mapping a [[ContractLike]] actor to a
    *          [[edsl.commitments.Contract Contract]] representing the value
    *          of the [[ContractLike]] actor's underlying
    *          [[edsl.commitments.Contract Contract]] to the `Counterparty` actor.
    * @return The total value of the `Counterparty` actor's stock of assets.
    * @note By definition, the value of equity is the difference between the
    *       value of the `Counterparty` actor's stock of assets and the value
    *       of the `Counterparty` actor's stock of liabilities.
    */
  def valueEquity(f: ActorRef => Contract): Contract = {
    And(valueAssets(f), Give(valueLiabilities(f)))
  }

  /** Compute the value of the `Counterparty` actor's stock of liabilities.
    *
    * @param f A function mapping a [[ContractLike]] actor to a
    *          [[edsl.commitments.Contract Contract]] representing the value
    *          of the [[ContractLike]] actor's underlying
    *          [[edsl.commitments.Contract Contract]] to the `Counterparty` actor.
    * @return The total value of the `Counterparty` actor's stock of liabilities.
    */
  def valueLiabilities(f: ActorRef => Contract): Contract = {
    balanceSheet.liabilities.map(f).reduce((contract1, contract2) => And(contract1, contract2))
  }

}
