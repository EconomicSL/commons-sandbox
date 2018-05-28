package acl

import akka.actor.ActorRef

import scala.collection.immutable


trait CounterPartyActor extends CommunicatingActor with
  QueryHandler[Contract] {

  var objects: immutable.Set[Contract]

  def counterPartyBehaviour: Receive = {
    case Add(contract) => objects += contract
    case Remove(contract) => objects -= contract
  }

  case class Add(contract: Contract)

  case class Remove(contract: Contract)

}
