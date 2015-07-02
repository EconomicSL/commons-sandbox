/*
Copyright 2015 David R. Pugh, Dan F. Tang, J. Doyne Farmer

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package tradables

import akka.actor.{Actor, ActorLogging, ActorRef}

import scala.collection.mutable


/** Trait describing a Tradable object. */
trait Tradable {
  this: Actor with ActorLogging =>

  /** Collection of actors for whom the tradable is an asset.
    * @note The possibility that a Tradable could have multiple owners is
    *       necessary in order to model [[https://en.wikipedia.org/wiki/Rivalry_(economics) rivalry]]
    *       (in the case of [[Tangible]] objects) and multi-lateral commitment (in the case of
    *       [[contracts.commitments.Contract]] objects.
    */
  def owners: mutable.Set[ActorRef]

 }
