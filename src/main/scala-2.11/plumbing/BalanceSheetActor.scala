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
package plumbing

import akka.actor.{Actor, ActorLogging}
import environment.{TradableLike, GoodLike}
import plumbing.tradables.PromiseLike

import scala.collection.mutable

/** Class representing a financial balance sheet.
  *
  * @note A [[BalanceSheetActor]] should be a child of some [[actor.EconomicActor]].
  */
abstract class BalanceSheetActor extends Actor
  with ActorLogging {

  /** Unordered collection of tradable objects. */
  def tradables: mutable.Set[TradableLike]

  /** Unordered collection of tradable object that are assets. */
  def assets: mutable.Set[TradableLike] = {
    tradables.filter {
      case tradable: GoodLike => true
      case tradable: PromiseLike if tradable.promisee == context.parent => true
      case _ => false
    }
  }

  /** Unordered collection of tradable object that are liabilities. */
  def liabilities: mutable.Set[TradableLike] = {
    tradables.filter {
      case tradable: PromiseLike if tradable.promisor == context.parent => true
      case _ => false
    }
  }

  def receive: Receive = {
    case PutTradable(tradable) =>
      tradables.add(tradable)
    case DeleteTradable(tradable) =>
      tradables.remove(tradable)

  }
}
