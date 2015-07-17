/*
Copyright 2015 David R. Pugh, Dan F. Tang, J. Doyne Farmer

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
specific language governing permissions and limitations under the License.

package actors

import acl.PromiseMakingActor
import akka.actor.{ActorLogging, Actor}
import edsl.CounterpartyActor


/** Base trait defining the behavior of all `EconomicActor` actors.
  *
  * Every `EconomicActor` is a [[acl.PromiseMakingActor `PromiseMakingActor`]] and communicates with other such
  * actors using the actor communication language defined in the [[acl `acl`]] package.
  *
  * Every `EconomicActor` is also a [[edsl.CounterpartyActor `CounterpartyActor`]] and communicates directly with
  * [[edsl.ContractActor `ContractActor`]] actors and indirectly with other
  * [[edsl.CounterpartyActor `CounterpartyActor`]] actors using [[edsl.ContractActor `ContractActor`]] actors as
  * communication "channels."
  */
trait EconomicActor extends Actor with ActorLogging with PromiseMakingActor with CounterpartyActor
*/