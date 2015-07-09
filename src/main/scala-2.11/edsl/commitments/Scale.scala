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


/** Scales an underlying [[Commitment]] by a potentially time-varying value.
  *
  * @param amount The scaling factor applied the the underlying [[Commitment]].
  * @param commitment The underlying [[Commitment]].
  * @note If you acquire `Scale(observable, commitment)`, then you acquire `commitment`
  *       at the same moment except that all rights and obligations of `commitment`
  *       are multiplied by the value of `observable` at the moment of
  *       acquisition.
  */
case class Scale[A](amount: Observable[A], commitment: Commitment) extends Commitment
