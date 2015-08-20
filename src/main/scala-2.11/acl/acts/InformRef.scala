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

import scala.collection.immutable


/** A message sent from some [[acl.CommunicatingActor `CommunicatingActor`]] (i.e., `sender`) to another
  * [[acl.CommunicatingActor `CommunicatingActor`]] (i.e., `receiver`) informing the `receiver` of object(s) which
  * satisfy some `descriptor`.
  *
  * @param conversationId is an expression used to identify a sequence of
  *                       [[acl.acts.CommunicativeAct `CommunicativeAct`]] messages that together form a conversation.
  * @param content is a collection of objects matching some `descriptor`.
  * @tparam A is the type of desired objects.
  * @note The `InformRef` message is sent by the `sender` using the [[acl.CommunicatingActor.informRef `informRef`]]
  *       action. The `InformRef` message is only sent in reply to a [[acl.acts.QueryRef `QueryRef`]] message.
  */
case class InformRef[A](conversationId: UUID, content: immutable.Iterable[A]) extends CommunicativeAct


/** Companion object for `InformRef`. */
object InformRef {

  /** Secondary constructor for `InformRef`.
    *
    * @param conversationId is an expression used to identify a sequence of
    *                       [[acl.acts.CommunicativeAct `CommunicativeAct`]] messages that together form a conversation.
    * @param content is the desired object.
    * @tparam A the type of the desired object.
    * @return an `InformRef` message.
    */
  def apply[A](conversationId: UUID, content: A): InformRef[A] = {
    InformRef[A](conversationId, immutable.Iterable(content))
  }

}

