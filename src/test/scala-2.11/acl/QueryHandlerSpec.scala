package acl

import akka.actor.ActorSystem
import akka.testkit.{TestActorRef, TestKit}

import scala.collection.immutable


class QueryHandlerSpec extends TestKit(ActorSystem("QueryHandlerSpec"))
  with BaseSpec {

  def afterAll(): Unit = {
    system.shutdown()
  }

  feature("A QueryHandler should be able to process QueryRef messages.") {

    //val queryHandlerRef = TestActorRef()

  }

  feature("A QueryHandler should be able to process QueryIf messages.") {
    ???
  }

}
