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


/** Provides classes for modeling a decentralized payments system.
  *
  * ==Overview==
  * "The logical origins of the money market can be traced, perhaps
  * surprisingly, to the operations of a decentralized payments system."
  *
  * The above quotation from Chapter 5 of Perry Mehrling's ''The New Lombard
  * Street'' provides an underlying motivation for our plumbing API. Taking as
  * given that money markets exist because of imperfections in a decentralized
  * payment system, answering any of the important (and policy-relevant!)
  * research questions surrounding the functioning of money markets requires an
  * explicit model of the underlying payment system.
  *
  * Of course, for some research questions, imperfections in the underlying
  * payments system may not play an important role.  In such cases it makes
  * sense to apply Occam's razor and consider some sort of idealized payments
  * system.
  *
  * ===Balance Sheets===
  * Every economic actor has its own balance sheet. We think of the economy as
  * a set of interlocking balance sheets in which the financial liabilities of
  * one economic actor are the financial assets of some other economic actor.
  * An economic actor's financial liabilities are composed of commitments to
  * pay (usually commitments to make a recurring series of payments at certain
  * points in the future).
  *
  * Balance sheets communicate with one another via contracts.
  *
  * ===Contracts===
  * Contracts communicate with balance sheets.
  *
  * ==General API requirements==
  * Current list of generic requirements for the `plumbing` API
  *
  *  - A payment system should be able to process both real and financial
  * transactions between economic actors.
  *
  *  - A payment system should be able to settle a transactions between two
  *  economic actors directly (i.e., without inter-mediation).
  *
  *  - A payment system should be able to settle a transactions between two
  *  economic actors indirectly (i.e., via inter-mediation by some other
  *  economic actor).
  *
  *  - The desired payment system for a particular model should be completely
  * configurable from some kind of `application.conf` file.
  *
  * ==Use cases==
  * I have taken the use cases more or less verbatim from ''Lecture 5: The
  * Central Bank as a Clearing House'' of Perry Mehrling's course on
  * [[https://www.coursera.org/course/money Money and Banking]].
  *
  * ===One big bank===
  * An important use case for us to consider is an idealized payments system
  * in which all economic agents have a deposit account and a line of credit at
  * a single, large bank. In this world, all payments involve nothing more than
  * book keeping operations for the bank.  This use case would serve as a
  * natural benchmark and could be used for models where we wish to abstract
  * from imperfections in the payment system (and money markets).
  *
  * There are at least two important sub use cases for our idealized payment
  * system:
  *
  *  - No overdrafts: Deposit account holders are required to hold non-negative
  *  account balances. Obvious measure of the money supply in this case is
  *  currency outstanding plus deposit balances. Note that this case represents
  *  a pure money payment system (i.e., there is no credit).
  *
  *  - Overdrafts: Deposit account holders can hold negative account balances
  *  (up to some limit). A negative deposit account balance is effectively an
  *  unsecured loan. Note that, with overdrafts, it is no longer obvious how
  *  to measure the money supply! Sensible measure would be the sum of currency
  *  outstanding, bank deposits, and overdrafts. Use of overdrafts as a method
  *  of payment means that this payment system is at least party credit based.
  *  The money supply can be expected to expand and contract over the business
  *  cycle (depending on the patterns of payments).
  *
  * ====Multiple banks====
  * Simplest use case for multiple banks is a payment system where all
  * interbank payments are made using reserves (either Gold or some other
  * reserve currency).
  *
  * Another use case would be bilateral intra-day netting.  Under bilateral
  * intra-day netting each bank nets payments to every other bank and pays only
  * the net in reserves.
  *
  * Third use case would be correspondent banking. With correspondent banking,
  * banks have deposit accounts at other banks which they use to make interbank
  * payments. Correspondent banking (with overdrafts) is basically another step
  * away from a money payment system towards a credit based payment system.
  * With correspondent banking the money supply is not constrained by the
  * quantity of reserves, but only by the various bilateral credit limits.
  *
  * ====Clearing houses====
  * In any system of bilateral interbank payments, any particular bank may at
  * the same time be making payments to one bank while receiving payments from
  * some other bank. A bank would like to be able to net payments across all of
  * its correspondents.
  *
  * Simple way to accomplish this would be for all banks to maintain correspondent
  * deposits at one bank and then use that bank's deposits to settle transactions.
  * In order to maintain elasticity of the money supply, we need to allow for
  * overdrafts.  However problems can arise if multiple banks require overdrafts at
  * the same time.
  *
  * Better system would be to have a group of banks form a clearing house. All member
  * banks treat all payments due to (from) other member banks as due to (from) the
  * clearing house. In this way member banks can accomplish multilateral netting
  * between one another during the day: each bank makes only a single net payment
  * at close of business. Settlement is done using clearing house certificates which
  * amount to a private form of reserves.  In the event that a member bank(s) is
  * unable to settle its account at the end of the day, then the member bank must
  * borrow from the clearing house.  The clearing house meets this demand for
  * reserves by creates additional reserves in the form of clearing house loan
  * certificates out of thin air. Clearing house loan certificates are typically
  * over collateralized (and pay interest).
  *
  * Private clearing houses play important roles in several modern financial systems
  * most notable in the U.S. ([[https://www.theclearinghouse.org/payments/chips CHIPS]])
  * and in the UK ([[http://www.chapsco.co.uk/ CHAPS]]).
  *
  * ====Central banking====
  * One way to view central banking is as a natural extension of clearing house
  * arrangements. A notable difference is that, with central banking, there is only
  * central bank reserves: no longer any difference between clearing house
  * certificates and clearing house loan certificates.
  *
  */
package object plumbing {

}
