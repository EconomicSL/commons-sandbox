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


/** Reverses the rights and obligations of the underlying `Contract`.
  * @param contract The underlying `Contract`.
  * @note Acquiring `Give(contract)` is the acquire all of `contract` rights
  *       and obligations (and vice versa). Note that for a bilateral contract
  *       between parties A and B, A acquiring `contract` implies that B must
  *       acquire `Give(contract)`.
  */
class Give(val contract: Contract) extends Contract

