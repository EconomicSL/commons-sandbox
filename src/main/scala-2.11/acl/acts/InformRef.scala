package acl.acts

/** A message sent from a [[acl.CommunicatingActor `CommunicatingActor`]] to another
  * [[acl.CommunicatingActor `CommunicatingActor`]] informing it of object(s) which satisfy some descriptor.
  *
  * @param descriptor is a function describing some required characteristics of the object.
  * @tparam D is the type of objects described by the `descriptor`.
  * @note The `InformRef` message is sent by a [[acl.CommunicatingActor `CommunicatingActor`]] using the
  *       [[acl.CommunicatingActor.informRef `informRef`]] action.
  */
case class InformRef[D](descriptor: (D) => Boolean) extends CommunicativeAct
