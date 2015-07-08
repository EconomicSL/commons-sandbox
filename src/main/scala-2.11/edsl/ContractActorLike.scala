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

import akka.actor.{Actor, ActorLogging, ActorRef}
import edsl.commitments.Commitment

import scala.collection.immutable


/** Trait defining the behavior for a generic contract actor. */
trait ContractActorLike extends Actor
  with ActorLogging {

  /** Mapping of a [[edsl.CounterpartyActor `CounterpartyActor`]] actor to a
    * [[edsl.commitments.Commitment `Commitment`]] representing that
    * [[edsl.CounterpartyActor `CounterpartyActor`]] actors obligations.
    */
  def commitments: immutable.Map[ActorRef, Commitment]

  /** [[edsl.CounterpartyActor `CounterpartyActor`]] actor for whom the underlying
    * [[edsl.commitments.Commitment `Commitment`]] represents a liability.
    */
  def issuer: ActorRef

  /** Collection of [[edsl.CounterpartyActor `Counterparty`]] actors for whom the underlying
    * [[edsl.commitments.Commitment `Commitment`]] represents an asset.
    */
  def owners: immutable.Set[ActorRef]

}
