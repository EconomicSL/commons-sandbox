package acl.acts

import acl.CommunicatingActor

import scala.collection.immutable

trait QueryHandler[A] {
  this: CommunicatingActor =>

  /** Collection of objects. */
  val objects: immutable.Iterable[A]

  def queryHandlerBehavior: Receive = {
    case message: QueryRef[A] =>
      val feasibleObjects = objects.filter(message.descriptor)
      message.selector match {
        case None =>
          informRef(message.conversationId, sender(), feasibleObjects)
        case Some(chooseOneOf) =>
          val choice = chooseOneOf(feasibleObjects)
          informRef(message.conversationId, sender(), choice)
      }

  }

}
