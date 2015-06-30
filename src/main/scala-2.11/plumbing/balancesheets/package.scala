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
package plumbing

import akka.actor.ActorRef


/** Provides classes for modeling actor balance sheets.
  *
  * ==Overview==
  * "To analyze how financial commitments affect the economy it is necessary to
  * look at economic units in terms of their cash flows. The cash-flow approach
  * looks at all units -- be they households, corporations, state and municipal
  * governments, or even national governments -- as if they were banks."
  *
  * The above quotation is taken from Hyman Minsky's magnum opus
  * ''Stabilizing an Unstable Economy''.  We view the economy as a system of
  * inter-locking balance sheets in which individuals We view every economic actor as an
  * entity that is both receiving certain cash flow events (i.e., receipts of
  * various kinds) as well as generating cash flow events (i.e., expenditures
  * of various kinds) over time.
  *
  * Most basic constraint that economic actors face in our framework is, to
  * again borrow from Minsky, the survival constraint: the inflow of cash flow
  * events must be at least as big as the outflow of generated cash flow
  * events.  By taking the "banking perspective" our primary concern is the
  * liquidity of economic actors; solvency of economic actors is a secondary
  * concern.
  *
  * Anytime an economic actor needs to make a payment it has only three
  * options:
  *
  *  1. Spend down hoards of money.
  *
  *  2. Liquidate accumulations of financial assets.
  *
  *  3. Increase accumulations of financial liabilities.
  *
  * Crucially both 2 and 3 depend on finding some other economic actor to take
  * the other side of the transaction. This may be easier said than done.  Only
  * option 1 is completely dependable.  Thus the import of money.
  *
  *
  * ===Requirements===
  *
  * - BalanceSheetActor should be able to add (remove) assets.
  *
  * - BalanceSheetActor should be able to add (remove) liabilities.
  *
  * - BalanceSheetActor should be able to value its assets.
  *
  * - BalanceSheetActor should be able to value its liabilities.
  *
  * - BalanceSheetActor should be able to compute its net worth.
  *
  */
package object balancesheets {

  /** Add a Tradable to the BalanceSheetActor's collection of assets. */
  case class AddAsset(tradable: ActorRef)

  /** Remove a Tradable from the BalanceSheetActor's collection of assets. */
  case class RemoveAsset(tradable: ActorRef)

  /** Add a Tradable to the BalanceSheetActor's collection of liabilities. */
  case class AddLiability(tradable: ActorRef)

  /** Remove a Tradable from the BalanceSheetActor's collection of liabilities. */
  case class RemoveLiability(tradable: ActorRef)

}
