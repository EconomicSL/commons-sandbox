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
package plumbing.contracts.commitments


/** Class for representing a conditional `Contract`.
  *
  * @param first A `Contract` object.
  * @param second Another `Contract` object.
  * @note If you acquire `Then(first, second)` and `first` contract has not
  *       expired, then you acquire the `first` contract. If the `first` contract
  *       has expired but the `second` contract has not, then you acquire the
  *       `second` contract.  The contract `Then(first, second)` expires only
  *       after both `first` and `second` contracts have expired.
  */
case class Then(first: Contract, second: Contract) extends Contract
