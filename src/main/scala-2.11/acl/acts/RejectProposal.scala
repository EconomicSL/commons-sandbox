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


/** A message sent from a [[acl.CommunicatingActor `CommunicatingActor`]] to a collection of other such actors
  * indicating that a previously received [[acl.acts.Propose `Propose`]] message has been rejected.
  *
  * @param conversationId is an expression used to identify an ongoing sequence of communicative acts that together
  *                       form a conversation.
  * @param proposal is the previously received [acl.Propose `Propose`] message that has been rejected.
  * @param reason is a proposition denoting the reason that the `proposal` has been rejected.
  * @tparam A is the type of action expression used to construct the content of the `proposal`.
  * @note The `RejectProposal` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor.rejectProposal `rejectProposal`]] action.
  */
case class RejectProposal[A](conversationId: UUID,
                             proposal: Propose[A], reason: (Beliefs) => Boolean) extends CommunicativeAct
