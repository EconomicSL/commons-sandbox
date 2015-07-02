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
package contracts.actors

import akka.actor.{ActorRef, Actor, ActorLogging}
import contracts.commitments.Contract

import scala.collection.mutable


/** Base trait for all ContractActors. */
trait ContractActorLike extends Actor
  with ActorLogging {

  /** Collection of commitments representing the terms of the contract. */
  def commitments: mutable.Map[ActorRef, Contract]

  /** Actor for whom the underlying commitment represents a liability. */
  def issuer: ActorRef

  /** Collection of actors for whom the underlying commitments represent assets. */
  def owners: mutable.Set[ActorRef]

}
