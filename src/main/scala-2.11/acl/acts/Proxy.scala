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
  * [[acl.CommunicatingActor `CommunicatingActor`]] in order to proxy some embedded message.
  *
  * @param message is the embedded [[acl.acts.CommunicativeAct `CommunicativeAct`]] which is being proxied.
  * @param descriptor is a proposition denoting a collection of actors to whom the `Proxy` message should be sent by
  *                   the `receiver`.
  * @param constraint is a proposition describing a termination condition for the propagation of the `message`.
  * @note The `Proxy` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor#proxy `proxy`]] action.
  */
case class Proxy[D](conversationId: UUID,
                    message: CommunicativeAct,
                    descriptor: (D) => Boolean,
                    constraint: (Beliefs) => Boolean) extends CommunicativeAct
