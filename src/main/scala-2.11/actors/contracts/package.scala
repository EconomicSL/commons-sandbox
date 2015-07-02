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
package actors

/** Classes for representing edsl as Akka Actors.
  *
  * ==Overview==
  * A `ContractActor` serves as a channel of acl between multiple
  * `BalanceSheetActors`.
  *
  * ===API Requirements===
  * Any `Actor` that implements the `ContractLike` trait should...
  *
  *  - have a collection of `commitments` representing the terms of the
  *  underlying `Contract`.
  *
  *  - have an `issuer` for whom the `Contract` represents a liability.
  *
  *  - have a collection of `owners` for whom the underlying `Contract`
  *  represents an asset.
  *
  *  - be able to modify its `issuer` (necessary in order to model the
  *  buying/selling of liabilities).
  *
  *  - be able to add (remove) members from its collection of `owners`.
  *
  *  - be able to communicate with a `BalanceSheetActor` in order to add
  *  (remove) itself from the collection of assets or liabilities of the
  *  `BalanceSheetActor`.
  */
package object contracts
