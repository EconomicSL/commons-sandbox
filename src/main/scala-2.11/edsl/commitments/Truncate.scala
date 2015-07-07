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
package edsl.commitments

import edsl.observables.Observable


/** The contract `Truncate(obs, contract)` is exactly like `contract` except
  * that it expires at the earlier of `date` and the horizon of the `contract`.
  *
  * @note `Truncate(date, contract)` only impacts the acquisition date of
  *       `contract` it does not in any way impact the rights and obligations
  *       defined in `contract` as such rights and obligations might extend
  *       well beyond `date`.
  */
case class Truncate[A](obs: Observable[A], contract: Commitment) extends Commitment

