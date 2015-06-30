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


/** Classes for modeling physical objects that are tradable.
  *
  * ==Overview==
  *
  * ===Requirements===
  * Any Actor implementing the Tradable trait needs to be able to:
  *
  *  1. Communicate with BalanceSheetActor: communication involves sending
  *  messages to AddAsset (RemoveAsset) to a BalanceSheetActor.
  *
  *  2. Alter own state in response to messages from owner of the tradable.
  *
  */
package object tradables {

}
