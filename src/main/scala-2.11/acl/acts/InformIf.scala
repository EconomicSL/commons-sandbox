/*
Copyright 2015 David R. Pugh, Dan F. Tang, J. Doyne Farmer

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
specific language governing permissions and limitations under the License.
*/
package acl.acts

import java.util.UUID

import acl.Beliefs


/** A message sent from some [[acl.CommunicatingActor `CommunicatingActor`]] (i.e., `sender`) to another
  * [[acl.CommunicatingActor `CommunicatingActor`]] (i.e., `receiver`) informing the `receiver` whether or not some
  * `proposition` is true.
  *
  * @param conversationId is an expression used to identify a sequence of communicative acts that together form a
  *                       conversation.
  * @param proposition is a proposition whose truth value the `sender` has knowledge of which it intends to share
  *                    with the `receiver`.
  * @note The `InformIf` message is sent by the `sender` using the [[acl.CommunicatingActor.informIf `informIf`]]
  *       action.
  */
case class InformIf(conversationId: UUID, proposition: (Beliefs) => Boolean) extends CommunicativeAct
