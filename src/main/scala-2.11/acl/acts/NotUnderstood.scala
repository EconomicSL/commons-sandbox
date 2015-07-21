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


/** A message sent from a [[acl.CommunicatingActor `CommunicatingActor`]] to to another
  * [[acl.CommunicatingActor `CommunicatingActor`]] informing it that some previously received message was not
  * understood.
  *
  * @param conversationId is an expression used to identify an ongoing sequence of communicative acts that together
  *                       form a conversation.
  * @param message is the [[acl.acts.CommunicativeAct `CommunicativeAct`]] that was not understood.
  * @param reason
  * @note The `NotUnderstood` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor.notUnderstood `notUnderstood`]] action.
  */
case class NotUnderstood(conversationId: UUID,
                         message: CommunicativeAct,
                         reason: (Beliefs) => Boolean) extends CommunicativeAct
