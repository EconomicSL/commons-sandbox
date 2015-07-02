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

/** Provides classes defining an embedded domain specific language (EDSL) that
  * is used to create composable commercial contracts.
  *
  * ==Overview==
  * Instead of defining a fixed catalogue of contracts our approach is to follow...
  *
  *   - [[https://lexifi.com/files/resources/MLFiPaper.pdf Peyton-Jones et al (2000)]]
  *   - [[http://www.itu.dk/~elsborg/sttt06.pdf Andersen et al (2006)]]
  *
  * ...and instead define a small set of atomic contracts, called
  * [[edsl.commitments commitments]], that can be used as "building blocks"
  * to construct ever more complex contracts.
  *
  * ===Composable Commercial Contracts===
  * Following [[http://www.itu.dk/~elsborg/sttt06.pdf Andersen et al (2006)]]
  * we focus on the following basic ''contract patterns'' for composing
  * commercial contracts from sub-contracts:
  *
  *  - A ''commitment'' stipulates the transfer of a resource or a set of
  *  resources between two parties; a commitment represents an ''atomic''
  *  `Contract`.
  *
  *  - A `Contract` may require ''sequential'' execution of its sub-contracts.
  *
  *  - A `Contract` may require ''concurrent'' execution of its sub-contracts,
  *  i.e., execution of all sub-contracts, where individual commitments are
  *  inter-leavened in any order.
  *
  *  - A `Contract` may require execution of one of a number of ''alternative''
  *  sub-contracts.
  *
  *  - A `Contract` may require ''repeated'' execution of a sub-contract.
  *
  * ==Overview==
  *
  * ===Requirements===
  * Current list of requirements for edsl is as follows...
  *
  *  1. Each contract has a collection of commitments.
  *
  *  2. Each contract must have both a face value (objective) and a present value (subjective).
  *
  *  3. Need a little language for modifying commitments.  Tentatively based
  *  around the Sources and Uses terminology from lecture 4 of Perry Mehrling's
  *  course on ''Money and Banking.''
  *
  * ===Composable Commercial Contracts===
  *
  * ==A language for Promises==
  * Having defined the concepts of a Good and a Promise as well as sets of
  * actions over Goods and Promises that can be performed by an Actor or groups
  * of Actors to complete the API we need to define a language (grammar?) for
  * building Sentences that describe valid Promises.
  *
  * ==Observables==
  * Every contract must have an Observable[T] which represents the stream of
  * data (or events?) that parties to the contract have used to condition
  * the specifics of the contract.
  *
  * Parties to the contract must subscribe as Observer[T] to the Observable.
  *
  * Examples
  * Need to build a catalogue of examples demonstrating how to build common
  * edsl using our language.
  */
package object edsl

