/*
Copyright 2015 David R. Pugh, Dan F. Tang, J. Doyne Farmer

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
specific language governing permissions and limitations under the License.
*/

/** Provides classes defining a low-level actor communication language (ACL).
  *
  * ==Overview==
  * The `acl` package defines a low-level actor communication language (ACL) as well as a
  * [[acl.CommunicatingActor `CommunicatingActor`]] behavioral trait that an actor must implement in order to
  * communicate with other such actors via the ACL.
  *
  * === Actor Communication Language ===
  * Our high-level actor communication language is influenced by, but not slave to, the [[http://www.fipa.org/
  * Foundation for Intelligent Physical Agents (FIPA)]] compliant
  * [[http://www.fipa.org/specs/fipa00037/SC00037J.pdf Agent Communication Language (ACL)]]. The FIPA actor
  * communication language
  *
  * ===`CommunicatingActor`===
  * Currently the [[acl.CommunicatingActor `CommunicatingActor`]] implements methods that send appropriately formatted
  * [[acl.acts.CommunicativeAct]] messages to target actors.  Eventually, the
  * [[acl.CommunicatingActor `CommunicatingActor`]] will also implement abstract protocols that will impose
  * additional structure on inter-actor communications.  Examples of such abstract communication protocols can
  * be found on the [[http://www.fipa.org/repository/standardspecs.html FIPA]] web site.
  *
  * Concrete implementations of these abstract protocols will define behavioral strategies that will be mixed
  * together to create an economic actor.
  *
  */
package object acl
