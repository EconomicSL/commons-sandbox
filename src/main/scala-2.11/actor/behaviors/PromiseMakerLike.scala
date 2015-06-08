/*
Copyright 2015 David R. Pugh, Dan F. Tang, J. Doyne Farmer

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package actor.behaviors

import actor.EconomicActor
import plumbing.contracts.PromiseLike


/** Trait defining behavior for an [[actor.EconomicActor]] that trades
  * [[plumbing.contracts.PromiseLike]] objects.
  *
  * == Overview ==
  *
  * ====Unilateral Actions involving Promises====
  * An [[PromiseMakerLike]] can unilaterally decide to perform any of the
  * following actions with a [[plumbing.contracts.PromiseLike]] tradable.
  *
  *  - `create` a new `PromiseLike`: When an actor creates a new `PromiseLike`, the
  * actor becomes its `promisor` and the new `PromiseLike` becomes a liability
  * for that actor. Similarly, the new Promise becomes an asset for which ever
  * actor is the `promisee`.
  *
  *  - `accept` a `PromiseLike`: If an actor ''k'' chooses to accept a `PromiseLike`
  * from another actor, then the `PromiseLike` is added as an asset to the balance
  * sheet of actor ''k'' and as a liability to the balance sheet of Actor ''j''.
  *
  *  - `reject` a Promise: An Actor ''k'' can always choose to reject (i.e., not
  *  accept a Promise from another Actor ''j''. Rejected contracts are not added to
  *  balance sheets.
  *
  *  - `fulfill` a Promise: An Actor ''k'' who is the promisor of a Promise may
  *  choose to perform actions specified in the Sentence when a certain `StateOfAffairs`
  *  has occurred. Once a Promise is successfully fulfilled, it is removed from
  *  both the balance sheet of its promisor and the balance sheet of its `promisee`.
  *
  *  - `break` a Promise: An Actor ''k'' who is the promisor of a Promise may
  *  choose not to perform actions specified in the Sentence when a certain
  *  `StateOfAffairs` has occurred. A decision to break a Promise is the same
  *  as a decision not to fulfill a Promise. Breaking a Promise may or may not
  *  have consequences. Should consequences be specified in the original
  *  Promise? I think so.
  *
  *  - `destroy` a Promise: An Actor ''k'' who is the promisee of a Promise may
  *  choose to destroy that Promise prior to a certain StateOfAffairs occurring.
  *  Once destroyed a Promise is removed from both the balance sheet of the
  *  promisee and the balance sheet of the promisor.
  *
  *  - `give` a Promise: An Actor ''k'' who is the promisee of a Promise may
  *  choose to give that Promise to another Actor ''j''.
  *
  *  - `redeem` a Promise:  An Actor ''k'' who is the promisee of a Promise may
  *  choose to redeem that Promise after a certain StateOfAffairs has occurred.
  *  Redemption of a Promise can be thought of as a request that the promisor of
  *  that Promise fulfill the Promise.
  *
  * ====Cooperative actions involving Promises====
  * Two Actors ''k'' and ''j'' can cooperate bi-laterally to perform additional
  * actions over Promises:
  *
  *  - `transfer` a Promise: An Actor ''k'' who is the promisee of the Promise
  *  can transfer a Promise to Actor ''j'' as follows:
  *
  *   1. Actor ''k'' give Promise to Actor ''j''.
  *
  *	  2. Actor ''j'' accept Promise from Actor ''k''.
  *
  *  - `exchange` of Promises: Two Actors ''k'' and ''j'' who are the promisees
  *  of different Promises can exchange these Promises with one another as
  *  follows:
  *
  *	   1. Actor ''j'' create new Promise `give` existing Promise to Actor ''k''.
  *
  *	   2. Actor ''j'' `transfer` new Promise to Actor ''k''.
  *
  *	   3. Actor ''k'' `transfer` existing Promise to Actor ''j''.
  *
  *	   4. Actor ''j'' `fulfill` Promise to Actor ''k''.
  *
  * A few things are worth noting about a bi-lateral exchange. First, by
  * choosing to fulfill the new Promise in step 4, Actor ''j'' gives his
  * existing Promise to Actor ''k'' (which completes the exchange). Second,
  * the new Promise issued by Actor ''j'' in step 1 involved a promise to give
  * and not a promise to transfer an existing  Promise to Actor ''k''.
  *
  * ```Question:``` Should it be possible to for an Actor to create a Promise
  * that commits other Actors to perform actions? Do we have any real world
  * examples?.
  *
  * Finally, note that an exchange could take place with Actor ''k'' creating
  * the new Promise in step 1. The important point is that either Actor ''k''
  * or Actor ''j'' (not necessarily both) must be able to credibly commit to
  * give its Promise upon receipt of the other's Promise. The credibility of
  * any particular Promise should be endogenously determined within the model
  * and not imposed by us a priori.
  *
  * It is interesting to compare the above bi-lateral exchange mechanism with
  * a multi-lateral exchange mechanism involving cooperation between three
  * Actors ''k'', ''j'', and ''k''. Two Actors ''k'', ''j'' who are the
  * promisees of different Promises can exchange these Promises using Actor
  * ''k'' as an intermediary as follows:
  *
  *  1. Actor ''k'' create new Promise give Actor ''j'' Promise to Actor ''k''\.
  *
  *  2. Actor ''k'' create new Promise give Actor ''k'' Promise to Actor ''j''\.
  *
  *  3. Actor ''k'' transfer new Promise to Actor ''k''.
  *
  *  4. Actor ''k'' transfer new Promise to Actor ''j''.
  *
  *  5. Actor ''k'' transfer existing Promise to Actor ''k''
  *
  *  6. Actor ''j'' transfer existing Promise to Actor ''k''
  *
  *  7. Actor ''k'' fulfill Promise to Actor ''k''
  *
  *  8. Actor ''k'' fulfill Promise to Actor ''j''
  *
  * An important feature of this multi-lateral process is that, so long as Actor
  * ''k'' can credibly commit to both Actors ''k'' and ''j'', then the exchange
  * between ''k'' and ''j'' can take place even if neither Actor ''k'' nor Actor
  * ''j'' can bi-laterally commit to give its Promise upon receipt of the
  * other's Promise. The difference between multi-lateral and bi-lateral commitment
  * has been stressed by many monetary theorists, in particular Kiyotaki and Moore
  * in a series of papers.
  *
  * There are several interpretations of the role that Actor ''k'' plays in the
  * above process.  One interpretation is that Actor ''k'' is functioning as a
  * central clearing party (CCP) for transactions between other Actors; another
  * more institutional interpretation is that Actor ''k'' is an actual Market.
  *
  * One final cooperative action needs to be specified: transfer of a Promise
  * that is liability for one Actor to some other Actor.  An Actor ''k'' who
  * is the promisor on a Promise can only transfer that Promise to another
  * Actor ''j'' with permission from the promisee of that Promise, Actor ''k''.
  *
  *  1. Actor ''k'' create new Promise give Actor ''j'' existing Promise.
  *
  *  2. Actor ''k'' accept new Promise.
  *
  *  3. Actor ''k'' fulfill Promise to Actor ''k''.
  *
  *  4. Actor ''j'' accept Promise from Actor ''k''.
  */
trait PromiseMakerLike {
  this: EconomicActor =>

  def create(): PromiseLike

  def destroy(promise: PromiseLike): Unit

  def fulfill(promise: PromiseLike): Unit

  def break(promise: PromiseLike): Unit

  def redeem(promise: PromiseLike): Unit

}
