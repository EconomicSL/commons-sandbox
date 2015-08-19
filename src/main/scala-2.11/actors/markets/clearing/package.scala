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
package actors.markets


/** Classes for modeling variou market clearing mechanisms.
  *
  * A `ClearingMechanismLike` actor should handle order execution (including
  * price formation and quantity determination as well as any necessary queuing
  * of buy and sell orders), generate filled orders, and send the filled orders
  * to some \texttt{SettlementMechanismLike} actor for further processing. Note
  * that each \texttt{MarketLike} actor should have a unique clearing mechanism.

\subsubsection{Order execution}
Order execution entails price formation and quantity determination. Market price formation requires clearing the market. It is important to be clear about the definition of the term ``market clearing.'' \cite{black2012dictionary} defines ``market clearing'' as follows:
\begin{enumerate}
  \item The process of moving to a position where the quantity supplied is equal to the quantity demanded.
  \item The assumption that economic forces always ensure the equality of supply and demand.
\end{enumerate}
In most all mainstream macroeconomic models (i.e., RBC, DSGE, etc) it is assumed that economic forces instantaneously adjust to ensure the equality of supply and demand in all markets.\footnote{
    I am sure that there are important examples in the mainstream economics literature where the process of market clearing is explicitly modeled and we should cite these.
} In our API, however, a key component of a \texttt{ClearingMechanismLike} actor is a \texttt{MatchingEngineLike} behavioral trait which explicitly defines a dynamic process by which orders are executed, prices are formed, and quantities are determined.

Note that a \texttt{MatchingEngineLike} behavioral trait is similar to an auction mechanism in many respects. Friedman (2007) lists four major types of two-sided auction mechanisms commonly implemented in real world markets.\footnote{
    TODO: similarly classify the various types of single-sided auction mechanisms commonly implemented in real world markets.
}
\begin{itemize}
  \item Posted offer (PO): PO allows one side (say sellers) to commit to particular prices that are publicly posted and then allows the other side to choose quantities. PO is the dominant clearing mechanism used in the modern retail sector.
  \item Bilateral negotiation (BLN): BLN requires each buyer to search for a seller (and vice versa); the pair then tries to negotiate a price and (if unsuccessful) resumes search. BLN clearing mechanisms were prevalent in pre-industrial retail trade, and continue to be widely used in modern business-to-business (B2B) contracting. Some retail Internet sites also use BLN clearing mechanisms.
  \item Continuous double auction (CDA): CDA allows traders to make offers to buy and to sell and allows traders to accept offers at any time during a trading period. Variants of CDA markets prevail in modern financial exchanges such as the New York Stock Exchange (NYSE), NASDAQ, and the Chicago Board of Trade and are featured options on many B2B internet sites.
  \item Call auction (CA): The CA requires participants to make simultaneous offers to buy or sell, and the offers are cleared once each trading period at a uniform price.
\end{itemize}
Each of these auction mechanisms would correspond to a particular implementation of an \texttt{MatchingEngineLike} behavior.

\subsubsection{Order queuing}
Order queuing involves storing and possibly sorting received buy and sell orders according to some \texttt{OrderQueuingStrategy}. Different order queuing strategies will be distinguished from one another by...
\begin{itemize}
    \item type of mutable collection used for storing buy and sell orders,
    \item the sorting algorithm applied to the mutable collections.
\end{itemize}
For example, some \texttt{OrderQueuingStrategy} behaviors might only require that unfilled buy and sell orders are stored in some mutable collection (the sorting of buy and sell orders within their respective collections being irrelevant). Other \texttt{OrderQueuingStrategy} behaviors might have complicated \texttt{OrderBookLike} rules for sorting the stored buy and sell orders.
*/
package object clearing
