/*
Copyright 2015 David R. Pugh, Dan F. Tang, J. Doyne Farmer

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
specific language governing permissions and limitations under the License.
*/
package acl

import java.util.UUID

import acl.acts.{Proxy, Propagate, CommunicativeAct}
import akka.actor.ActorRef

import scala.collection.mutable


/** Trait defining behavior of a [[acl.CommunicatingActor `CommunicatingActor`]] that is also a node in some graph. */
trait NodeActor {
  this: CommunicatingActor =>

  /** Collection of actors to whom the `CommunicatingActor` can directly send a message. */
  def neighbors: mutable.Set[ActorRef]

  /** Propagate a message to another `CommunicatingActor` satisfying a descriptor.
    *
    * @param conversationId is an expression used to identify an ongoing sequence of
    *                       [[acl.acts.CommunicativeAct `CommunicativeAct`]] that together form a conversation.
    * @param receiver is the `CommunicatingActor` to whom the [[acl.acts.Propagate `Propagate`]] message should be sent.
    * @param message is the embedded [[acl.acts.CommunicativeAct `CommunicativeAct`]] which is being propagated.
    * @param descriptor is a proposition denoting a collection of actors to whom the [[acl.acts.Propagate `Propagate`]]
    *                   message should be sent by the `receiver`.
    * @param constraint is a proposition describing a termination condition for the propagation of the `message`.
    * @note The `propagate` act works as follows:
    *
    *       1. The `CommunicatingActor` requests the `receiver` to treat the embedded message in the
    *       received [[acl.acts.Propagate `Propagate`]] message as if it is was directly sent from the
    *       `CommunicatingActor`, that is, as if the `CommunicativeActor` performed the embedded communicative act
    *       directly to the `receiver`.
    *
    *       2. The `CommunicatingActor` wants the `receiver` to identify other actors denoted by the given `descriptor`
    *       and to send the received [[acl.acts.Propagate `Propagate`]] message to them.
    *
    *       This communicative act is designed for delivering messages through federated agents by creating a chain
    *       (or tree) of [[acl.acts.Propagate `Propagate`]] messages.
    */
  def propagate(conversationId: UUID,
                receiver: ActorRef,
                message: CommunicativeAct,
                descriptor: (ActorRef) => Boolean,
                constraint: (Beliefs) => Boolean): Unit = {
    receiver ! Propagate(conversationId, message, descriptor, constraint)
  }

  /** Request another `CommunicatingActor` to send a message to a collection of other actors matching a given
    * descriptor (possibly subject to some constraint).
    *
    * @param conversationId is an expression used to identify an ongoing sequence of
    *                       [[acl.acts.CommunicativeAct `CommunicativeAct`]] that together form a conversation.
    * @param receiver is the `CommunicatingActor` to whom the [[acl.acts.Proxy `Proxy`]] message should be sent.
    * @param message is the embedded [[acl.acts.CommunicativeAct `CommunicativeAct`]] which is being proxied.
    * @param descriptor is a proposition denoting a collection of actors to whom the [[acl.acts.Proxy `Proxy`]] message
    *                   should be sent by the `receiver`.
    * @param constraint is a proposition constraining the proxying of the `message`.
    * @note The `CommunicatingActor` informs the `receiver` that it wants the `receiver` to identify actors that
    *       satisfy the given `descriptor` and forward them the embedded `message`.
    */
  def proxy(conversationId: UUID,
            receiver: ActorRef,
            message: CommunicativeAct,
            descriptor: (ActorRef) => Boolean,
            constraint: (Beliefs) => Boolean): Unit = {
    receiver ! Proxy(conversationId, message, descriptor, constraint)
  }

}
