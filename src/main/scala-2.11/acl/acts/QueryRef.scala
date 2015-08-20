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
import scala.reflect.runtime.universe._

/** A message sent from some [[acl.CommunicatingActor `CommunicatingActor`]] (i.e., `sender`) to another
  * [[acl.CommunicatingActor `CommunicatingActor`]] (i.e., `receiver`) asking whether or not the `receiver` has
  * references to objects matching a given `descriptor`.
  *
  * @param conversationId is an expression used to identify a sequence of communicative acts that together form a
  *                       conversation.
  * @param descriptor is a function describing some required characteristics of an object.
  * @param selector is a rule for choosing an object from the collection of objects satisfying the `descriptor`.
  * @tparam A is the type of desired object.
  * @note The `QueryRef` message is sent by the `sender` using the [[acl.CommunicatingActor.queryRef `queryRef`]]
  *       method.
  */
case class QueryRef[A : TypeTag](conversationId: UUID,
                                 descriptor: (A) => Boolean,
                                 selector: Option[(immutable.Iterable[A]) => A]) extends CommunicativeAct
