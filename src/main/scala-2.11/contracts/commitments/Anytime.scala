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
package contracts.commitments


/** A contract that creates an obligation to acquire an underlying contract
  * at some point between the acquisition of the contract and the expiration
  * of the underlying contract.
  * @param contract The `Contract` which must eventually be acquired.
  * @note If an economic actor acquires `Anytime(contract)`, then it must
  *       acquire `contract`, but is free to do so at any point between the
  *       acquisition of `Anytime(contract)` and the expiration of `contract.`
  *       The contract `Anytime(contract)` expires when `contract` expires.
  */
class Anytime(val contract: Contract) extends Contract
