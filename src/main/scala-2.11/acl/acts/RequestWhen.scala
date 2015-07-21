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
  * requesting them to perform some action when some given precondition becomes true.
  *
  * @param content is an action expression defining the action(s) that the `sender` is requesting the `receiver` to
  *                perform.
  * @param precondition is a proposition defining the precondition that should be satisfied in order for the
  *                     `receiver` to perform the action(s) specied in the `content`.
  * @tparam A is the type of action expression used to construct the content of the `request`.
  * @note The `RequestWhen` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor.requestWhen `requestWhen`]] action.
  */
case class RequestWhen[A](conversationId: UUID,
                          content: A,
                          precondition: (Beliefs) => Boolean) extends CommunicativeAct
