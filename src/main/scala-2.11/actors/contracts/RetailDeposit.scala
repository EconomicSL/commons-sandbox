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
package actors.contracts

import akka.actor.ActorRef
import edsl.commitments.Contract

import scala.collection.mutable


/** Class defining a commercial bank deposit.
  *
  * @param balance Amount of the underlying deposit contract.
  * @param code String identifying the underlying currency.
  * @param issuer Actor for whom the underlying deposit contract is a liability.
  * @param owners Actor(s) for whom the underlying deposit contract is an asset.
  */
case class RetailDeposit(var balance: Double,
                              code: String,
                              issuer: ActorRef,
                              owners: mutable.Set[ActorRef]) extends DepositLike {

  def commitments: mutable.Map[ActorRef, Contract] = {
    ???
  }

  def receive: Receive = {
    ???
  }

}
