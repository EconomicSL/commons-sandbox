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


/** Base trait representing various types of requests. */
sealed trait RequestActLike[A] extends CommunicativeAct {

  def content: A
}


/** A message sent from some [[acl.CommunicatingActor `CommunicatingActor`]] (i.e., `sender`) to another
  * [[acl.CommunicatingActor `CommunicatingActor`]] (i.e., `receiver`) requesting the `receiver` to perform some action.
  *
  * @param conversationId is an expression used to identify a sequence of communicative acts that together form a
  *                       conversation.
  * @param content is an action expression defining the action(s) that the `sender` is requesting the `receiver` to
  *                perform.
  * @note The `Request` message is sent by the `sender` using the [[acl.CommunicatingActor.request `request`]] action.
  */
case class Request[A](conversationId: UUID, content: A) extends RequestActLike[A]


/** A message sent from some [[acl.CommunicatingActor `CommunicatingActor`]] (i.e., `sender`) to another
  * [[acl.CommunicatingActor `CommunicatingActor`]] (i.e., `receiver`) requesting the `receiver` to perform some action
  * when some given `precondition` becomes true.
  *
  * @param conversationId is an expression used to identify a sequence of communicative acts that together form a
  *                       conversation.
  * @param content is an action expression defining the action(s) that the `sender` is requesting the `receiver` to
  *                perform.
  * @param precondition is a proposition defining the precondition that should be satisfied in order for the
  *                     `receiver` to perform the action(s) specified in the `content`.
  * @tparam A is the type of action expression used to construct the content of the `request`.
  * @note The `RequestWhen` message is sent by the `sender` using the
  *       [[acl.CommunicatingActor.requestWhen `requestWhen`]] action.
  */
case class RequestWhen[A](conversationId: UUID,
                          content: A,
                          precondition: (Beliefs) => Boolean) extends RequestActLike[A]


/** A message sent from some [[acl.CommunicatingActor `CommunicatingActor`]] (i.e., `sender`) to another
  * [[acl.CommunicatingActor `CommunicatingActor`]] (i.e., `receiver`) requesting the `receiver` to perform some action
  * when some given `precondition` becomes true and thereafter each time the `precondition` becomes true again.
  *
  * @param conversationId is an expression used to identify a sequence of communicative acts that together form a
  *                       conversation.
  * @param content is an action expression defining the action(s) that the `sender` is requesting the `receiver` to
  *                perform.
  * @param precondition is a proposition defining the precondition that should be satisfied in order for the
  *                     `receiver` to perform the action(s) specified in the `content`.
  * @tparam A is the type of action expression used to construct the content of the `request`.
  * @note The `RequestWhenever` message is sent by the `sender` using the
  *       [[acl.CommunicatingActor.requestWhenever `requestWhenever`]] action.
  */
case class RequestWhenever[A](conversationId: UUID,
                              content: A,
                              precondition: (Beliefs) => Boolean) extends RequestActLike[A]



