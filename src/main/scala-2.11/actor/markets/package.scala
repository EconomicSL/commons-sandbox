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
package actor

/** Provides classes for modeling markets.
  *
  * ==Overview==
  * The Markets API explicitly defines various disequilbrium dynamic processes
  * by which market prices and quantities are determined.
  *
  * ===Requirements===
  * The Markets API needs to be sufficiently flexible in order to handle markets
  * for relatively homogeneous goods such as firm non-labor inputs, firm outputs,
  * final consumption goods, standard financial products etc., as well as markets
  * for relative heterogeneous goods such as labor, housing, non-standard
  * financial products, etc.
  *
  * Here is my (likely incomplete) list of requirements for the Markets API
  *
  *  - Receive buy and sell orders from other actors.
  *
  *  - Accept (reject) only valid (invalid) buy and sell orders.
  *
  *  - Handle queuing of accepted buy and sell orders as necessary.
  *
  *  - Order execution including price formation and, if necessary, quantity
  *  determination.
  *
  *  - Processing and settlement of executed orders once those orders have been
  *  filled.
  *
  *  - Record keeping of orders received, orders executed, transactions
  *  processed, etc.
  *
  *  Implementation issue: too many requirements for a single market actor to satisfy.
  *  Solution: model the market actor as a ''collection'' of actors. Specifically,
  *  suppose that each `MarketLike` actor is composed of two additional actors:
  *  a `ClearingMechanismLike` actor that models the clearing process of buy and
  *  sell orders, and then a `SettlementMechanismLike` mechanism that processes
  *  the resulting filled orders.

\subsection{\texttt{MarketLike} actor}
The \texttt{MarketLike} actor should directly receive buy and sell orders for a particular \texttt{Tradable}, filter out any invalid orders, and then forward along all valid orders to a \texttt{ClearingMechanismLike} actor for further processing.

\section{Use cases for \texttt{MarketLike} actors}

\subsection{Specific use cases for \texttt{MarketLike} actors}
In this section I sketch out some specific use cases for the Markets API.

\subsubsection{Retail goods market}
\texttt{RetailMarketLike} behavior would extend \texttt{MarketLike} behavior with:
\begin{itemize}
    \item Clearing mechanism with \texttt{PostedOfferLike} matching engine,
    \item \texttt{BilateralSettlement} settlement mechanism.
\end{itemize}
Retail goods markets are markets for final consumption goods (typically purchased by households).

\subsubsection{Wholesale goods market}
\texttt{WholesaleMarketLike} behavior would extend \texttt{MarketLike} behavior with:
\begin{itemize}
    \item Clearing mechanism with \texttt{BilateralNegotiationLike} matching engine,
    \item \texttt{BilateralSettlement} settlement mechanism.
\end{itemize}
Wholesale goods markets are markets for intermediate goods (typically purchased by firms and then used in the production of retail goods).

\subsubsection{Labor market}
\texttt{LaborMarketLike} behavior would extend \texttt{MarketLike} behavior with:
\begin{itemize}
    \item Clearing mechanism with either \texttt{BilateralNegotiationLike} or \texttt{PostedOffer} matching engines,
    \item \texttt{BilateralSettlement} settlement mechanism.
\end{itemize}
Labor markets are tricky.  If we use \texttt{BilateralNegotiationLike} clearing mechanism then we can link into the massive search and match literature.

\subsubsection{Housing market}
\texttt{HousingMarketLike} behavior would extend \texttt{MarketLike} behavior with:
\begin{itemize}
    \item Clearing mechanism with \texttt{PostedOfferLike} matching engine,
    \item \texttt{BilateralSettlement} settlement mechanism.
\end{itemize}
Note similarity of \texttt{HousingMarketLike} to \texttt{RetailMarketLike}

\subsubsection{Securities market}
\texttt{SecuritiesMarketLike} behavior would extend \texttt{MarketLike} behavior with:
\begin{itemize}
    \item Clearing mechanism with \texttt{ContinuousDoubleAuctionLike} matching engine and \texttt{OrderBookLike} order queuing strategy,
    \item \texttt{CentralCounterpartySettlement} settlement mechanism.
\end{itemize}
Securities markets would include markets for stocks, bonds, and currencies. Could even create a \texttt{SecuritiesExchange} actor which would route orders for various securities to the appropriate \texttt{SecuritiesMarketLike} actor.

\subsubsection{Unsecured interbank lending market}
\texttt{InterbankMarketLike} behavior would extend \texttt{MarketLike} behavior with:
\begin{itemize}
    \item Clearing mechanism with \texttt{BilateralNegotiationLike} matching engine,
    \item \texttt{BilateralSettlement} settlement mechanism.
\end{itemize}
See Perry Mehrling for more details on unsecured interbank lending markets.

\subsubsection{Secured interbank lending (repo) market}
\texttt{RepoMarketLike} behavior would extend \texttt{MarketLike} behavior with:
\begin{itemize}
    \item Clearing mechanism with \texttt{BilateralNegotiationLike} matching engine,
    \item \texttt{BilateralSettlement} settlement mechanism.
\end{itemize}
See Perry Mehrling for more details on secured interbank lending (repo) markets.

  */
package object markets
