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
import environment.tradables.TradableLike

/** Provides classes for modeling a decentralized payments system.
  *
  * ==Overview==
  * "The logical origins of the money market can be traced, perhaps surprisingly,
  * to the operations of a decentralized payments system."
  *
  * The above quotation from Chapter 5 of Perry Mehrling's ''The New Lombard Street''
  * provides an underlying motivation for our plumbing API. Given that money
  * markets exist because of imperfections in a decentralized payment system,
  * in order to answer any of the important, policy-relevant research questions
  * related to money markets we need to explicitly model the payment system.
  *
  * Of course, for some research questions, imperfections in the underlying payments
  * system may not play an important role.  In such cases it makes sense to apply
  * Occam's razor and consider some sort of idealized payments system.
  *
  * ===API requirements===
  * 1. Payment system needs to be able to handle inter-bank payments between banks.
  * 2. Payment system needs to interface with markets.
  *
  * ===Use cases===
  * An important use case for us to consider is an idealized payments system in
  * which all economic agents have a deposit account and a line of credit at a
  * single, large bank. In this world, all payments involve nothing more than
  * book keeping operations for the bank.  This use case would serve as a natural
  * benchmark model and could be used for models where we wish to abstract from
  * imperfections in the payment system (and money markets).
  *
  * Another important use case to consider is a two-tier payments system: some
  * number of private banks that need to make inter-bank payments to one another
  * using central bank reserves.
  *
  * Extension #1: All private banks need to make inter-bank payments. Typically,
  * however, only a small number of private banks will have reserve accounts at
  * a central bank. Banks without reserve accounts at the central bank will need
  * to borrow needed reserves in the repo money market.
  *
  * Extension #2: Another use case, possibly only relevant in multi-country models,
  * would be something like the Eurodollar market (i.e., a repo market in foreign
  * exchange).
  */
package object plumbing {

  case class PutTradable(tradable: TradableLike)

  case class DeleteTradable(tradable: TradableLike)

}
