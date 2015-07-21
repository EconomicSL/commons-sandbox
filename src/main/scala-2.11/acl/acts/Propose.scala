package acl.acts

import acl.Beliefs

/**
 * Created by drpugh on 7/21/15.
 */
case class Propose[A](content: A,
                      precondition: (Beliefs) => Boolean,
                      inReplyTo: Option[Propose[A]]) extends CommunicativeAct
