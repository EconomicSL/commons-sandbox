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

import acl.acts._
import akka.actor.{ActorLogging, Actor}

import scala.reflect.runtime.universe._
import scala.util.{Success, Failure, Try}


trait RequestHandlingActor {
  this: CommunicatingActor with Actor with ActorLogging =>
  
  /** Decide whether to agree or refuse a request.
    *
    * @param request
    * @return
    */
  def handle[A](request: Request[A]): Try[ResponseActLike]

  /** Decide whether to agree or refuse a requestWhen.
    *
    * @param request
    * @return
    */
  def handle[A](request: RequestWhen[A]): Try[ResponseActLike]

  /** Decide whether to agree or refuse a RequestWhenever.
    *
    * @param request
    * @return
    */
  def handle[A](request: RequestWhenever[A]): Try[ResponseActLike]

  def receive: Receive = {
    case msg: RequestActLike =>
      val response = handle(msg)
      response match {
        case Success(Agree(id, request, precondition)) =>  // actor agrees to the request
          ???
        case Success(Refuse(id, request, reason)) =>  // actor refuses the request
          ???
        case Failure(ex) =>  //actor fails to understand request
      }

    case msg: Cancel =>
      ???
  }

}
