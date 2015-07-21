package acl.acts

import acl.Beliefs

/** A message sent from a [[acl.CommunicatingActor `CommunicatingActor`]] to a collection of other such actors asking
  *  whether or not a given proposition is true.
  *
  * @param proposition is a proposition about which the `CommunicatingActor` is ignorant (i.e., has no knowledge of its
  *                    truth value).
  * @note the `QueryIf` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor.queryIf, `queryIf`]] method.
  */
case class QueryIf(proposition: (Beliefs) => Boolean) extends CommunicativeAct
