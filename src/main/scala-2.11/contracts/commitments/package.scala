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
package contracts

import rx.lang.scala.Observable

/** Provides classes for defining commitments.
  *
  * ==Overview==
  * A ''commitment'' stipulates the transfer of a resource or a set of
  * resources between two parties; a commitment represents an ''atomic''
  * `Contract`.
  *
  * We envision that:
  *
  *   1. model builders will use commitments, as well as the higher-level
  *   contracts in our library, to construct commonly encountered contracts
  *   for their specific use cases.
  *
  *   2. actors within a model can use commitments, as well as the
  *   higher-level contracts in our library, to innovate previously unknown
  *   contracts.
  *
  * ===Requirements===
  *
  * ====Payments systems====
  * To model a "one big bank" payment system, we need...
  *
  * - a representation of retail deposits
  *
  * - a representation of currency
  *
  * - a representation of an un-collateralized overdraft
  *
  * We can model both the base "multiple banks" payment system as well as a payment
  * payment system with bilateral intra-day netting by adding...
  *
  *  - a representation of reserve deposits
  *
  * To model a payment system with correspondent banking we need...
  *
  * - a representation of correspondent deposits (which can probably be a simple
  * extension of retail deposits).
  *
  */
package object commitments
