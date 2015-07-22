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
  * [[acl.CommunicatingActor `CommunicatingActor`]] (i.e., `receiver`) indicating that a previously received
  * [[acl.acts.Request `Request`]] message (i.e., `request`) to perform some action should be cancelled.
  *
  * @param conversationId is an expression used to identify a sequence of communicative acts that together form a
  *                       conversation.
  * @param request is the previously received `request` to perform some action that should be cancelled.
  * @tparam A is the type of action expression used to construct the content of the `request`
  * @note The `Cancel` message is sent the `sender` using the [[acl.CommunicatingActor.cancel `cancel`]] action.
  */
case class Cancel[A](conversationId: UUID, request: Request[A]) extends CommunicativeAct
