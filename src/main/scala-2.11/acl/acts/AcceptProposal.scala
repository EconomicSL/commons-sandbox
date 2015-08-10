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

/** A message sent from some [[acl.CommunicatingActor `CommunicatingActor`]] (i.e., `sender`) to another
  * [[acl.CommunicatingActor `CommunicatingActor`]] (i.e., `receiver`) indicating that the `sender` has accepted a
  * previously received [[acl.acts.Propose `Propose`]] message (i.e., `proposal`) from the `receiver`.
  *
  * @param conversationId is used to identify a sequence of [[acl.acts.CommunicativeAct `CommunicativeAct`]] messages
  *                       that together form a conversation.
  * @param proposal is the previously received `proposal` that the `sender` has decided to accept.
  * @note The `AcceptProposal` message is sent by the `sender` using the
  *       [[acl.CommunicatingActor.acceptProposal `acceptProposal`]] action.
  */
case class AcceptProposal(conversationId: UUID, proposal: Propose) extends CommunicativeAct
