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
package plumbing.balancesheets

import akka.actor.{ActorRef, Actor, ActorLogging}

import scala.collection.mutable


/** Class representing a balance sheet.
  * @note A [[BalanceSheetActor]] should be a child of some [[coms.EconomicActorLike]] actor.
  */
abstract class BalanceSheetActor extends Actor
  with ActorLogging {

  /** Unordered collection of tradable objects that are assets. */
  def assets: mutable.Set[ActorRef]

  /** Unordered collection of tradable objects that are liabilities. */
  def liabilities: mutable.Set[ActorRef]

  def receive: Receive = {
    case AddAsset(asset) =>
      assets.add(asset)
    case RemoveAsset(asset) =>
      assets.remove(asset)
    case AddLiability(liability) =>
      liabilities.add(liability)
    case RemoveLiability(liability) =>
      liabilities.remove(liability)

  }
}
