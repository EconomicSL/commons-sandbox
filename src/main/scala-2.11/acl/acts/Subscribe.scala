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


/** A message sent from a [[acl.CommunicatingActor `CommunicatingActor`]] to another
  * [[acl.CommunicatingActor `CommunicatingActor`]] requesting that the receiving actor notify the sender of the
  * value of a reference whenever the object identified by the reference changes.
  *
  * @param conversationId is an expression used to identify an ongoing sequence of communicative acts that together
  *                       form a conversation.
  * @param descriptor is a function describing some required characteristics of the reference object.
  * @note The `Subscribe` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor.subscribe `subscribe`]] action.
  */
case class Subscribe[D](conversationId: UUID, descriptor: (D) => Boolean) extends CommunicativeAct
