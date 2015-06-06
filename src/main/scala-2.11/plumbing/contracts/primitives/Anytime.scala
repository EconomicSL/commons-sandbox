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
package plumbing.contracts.primitives

import java.time.LocalDate


/**
  *
  * @note If you acquire `Anytime(date, contract)` you must acquire `contract`,
  *       but you are free to do so at any `date` between the acquisition date
  *       of `Anytime(date, contract)` and the expiry date of `contract.` The
  *       contract `Anytime(date, contract)` expires when `contract` expires.
  */
case class Anytime(date: LocalDate, contract: Contract) extends Contract
