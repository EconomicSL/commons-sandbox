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


/** A message sent from a [[acl.CommunicatingActor `CommunicatingActor`]] to a collection of other such actors
  * requesting proposals that satisfy certain preconditions.
  *
  * @param content is an action expression defining the action(s) that the `sender` is requesting the `receiver` to
  *                submit a proposal to perform.
  * @param precondition is a proposition defining conditions that any submitted proposal must satisfy in order to be
  *                     accepted.
  * @tparam A is the type of action expression used to construct the `proposal`.
  */
case class CallForProposal[A](conversationId: UUID,
                              content: A, precondition: (Propose[A]) => Boolean) extends CommunicativeAct
