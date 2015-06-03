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
package plumbing.tradables

import akka.actor.ActorRef
import environment.TradableLike


/** Trait defining a tradable object representing promise to deliver a physical
  *  good or commodity.
  */
trait PromiseLike extends TradableLike {

  /** [[ActorRef]] of the [[actor.EconomicActor]] for whom the promise is an asset. */
  def promisee: ActorRef

  /** [[ActorRef]] of the [[actor.EconomicActor]] for whom the promise is a liability. */
  def promisor: ActorRef

  /** Face value of the promise in units of ???. */
  def faceValue: Double

}
