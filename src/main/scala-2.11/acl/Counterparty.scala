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
package acl

import akka.actor.{Actor, ActorLogging, ActorRef}
import edsl.ContractBroken

/** Trait defining behavior for an actor that is party to some
  * [[edsl.commitments.Contract Contract]].
  *
  * `Counterparty` actors communicate with other `Counterparty` actors using
  * [[ContractLike ContractLike]] actors as "channels."
  */
trait Counterparty {
  this: Actor with ActorLogging =>

  /** The act of breaking the terms of some existing [[edsl.commitments.Contract Contract]].
    *
    * @param receiver a [[ContractLike]] actor whose underlying
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



}
