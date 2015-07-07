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

import akka.actor.ActorRef

import scala.collection.mutable


/** Base trait for representing a balance sheet.
  *
  * We think of every actor in the economy as a kind of "bank", in the sense
  * that every actor needs to be monitoring its inflow and outflow of money
  * balances. The current state of represents the current state of its
  * money balances and stores collections of [ContractLike] actors representing
  * contracts which will generate modey inflows (i.e., assets)
  * cash flow events (i.e., receipts of various kinds) as well as liabilities
  * cash flow events (i.e., expenditures of various kinds) over time.
  *
  * A balance sheet represents the state of an economic actor's assets, liabilities, and equity at a particular point in time. In particular, the balance sheet details the balance of cash inflows and cash outflows over the preceding period.
  */
trait BalanceSheetLike {

  /** Unordered collection of tradable objects that are assets. */
  def assets: mutable.Set[ActorRef]

  /** Unordered collection of tradable objects that are liabilities. */
  def liabilities: mutable.Set[ActorRef]

}
