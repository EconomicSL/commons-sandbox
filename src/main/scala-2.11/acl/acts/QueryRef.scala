package acl.acts

/** A message sent from a [[acl.CommunicatingActor `CommunicatingActor`]] to a collection of other such actors asking
  * whether or not the receiving actors have references to objects matching a given descriptor.
  *
  * @param descriptor is a function describing some required characteristics of an object.
  * @tparam D is the type of object characterized by the `descriptor`.
  * @note the `QueryIf` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor.queryIf, `queryIf`]] method.
  */
case class QueryRef[D](descriptor: (D) => Boolean) extends CommunicativeAct
