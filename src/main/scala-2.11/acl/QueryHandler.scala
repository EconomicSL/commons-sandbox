package acl

import acl.acts.{QueryIf, QueryRef}

import scala.collection.immutable


/** Trait describing behavior of a [[acl.CommunicatingActor `CommunicatingActor`]] that handles
  * [[acl.acts.QueryRef `QueryRef`]] and [[acl.acts.QueryIf `QueryIf`]] messages.
  */
trait QueryHandler[A] {
  this: CommunicatingActor =>

  /** Collection of objects. */
  def objects: immutable.Iterable[A]

  def queryHandlerBehavior: Receive = {
    case message: QueryRef[A] =>
      val interestingObjects = objects.filter(message.descriptor)
      message.selector match {
        case None =>
          informRef(message.conversationId, sender(), interestingObjects)
        case Some(selectOneOf) =>
          val selection = selectOneOf(interestingObjects)
          informRef(message.conversationId, sender(), selection)
      }
    case message: QueryIf =>
      ???

  }

}
