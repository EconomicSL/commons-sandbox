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
  * indicating that a previously received [[acl.acts.Request `Request`]] message to perform some action should be
  * cancelled.
  *
  * @param request is the previously received [acl.Request `Request`] message to perform some action that has been
  *                cancelled.
  * @tparam A is the type of action expression used to construct the content of the `request`
  * @note The `Cancel` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor.cancel `cancel`]] action.
  */
case class Cancel[A](conversationId: UUID, request: Request[A]) extends CommunicativeAct
