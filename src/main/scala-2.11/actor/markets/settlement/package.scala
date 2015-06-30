package actor.markets

/** Classes for modeling settlement mechanisms.
  *
  * \subsection{Settlement mechanisms}
Fundamental objective of a \texttt{SettlementMechanismLike} actor is to convert filled orders into settled transactions. Rough sketch of a process by which filled orders are converted into settled transaction is as follows.
\begin{itemize}
    \item Receive filled orders from some \texttt{ClearingMechanismLike} actor(s).
    \item Send request for the desired quantity of the specified \texttt{Tradable} to the seller.
    \item Send request for some desired quantity of the specified means of payment (which will be some other \texttt{Tradable}) to the buyer.
    \item Handle response from the seller (requires handling the case in which seller has insufficient quantity of the specified \texttt{Tradable}).
    \item Handle response from the buyer (requires handling the case in which buyer has insufficient quantity of the specified means of payment).
    \item Generate a settled transaction.
\end{itemize}
The following two types of settlement mechanisms should cover most all possible use cases.
\begin{itemize}
    \item Bilateral settlement: with bilateral settlement, buy and sell counterparties settle directly with one another.
    \item Central counterparty (CCP) settlement: with CCP settlement, a central counterparty (CCP) actor inserts itself as a both a buy and sell counterparty to all filled orders that it receives from some clearing mechanism. After inserting itself as a counterparty, the CCP actor then settles the filled orders using bilaterally.
\end{itemize}
Unlike clearing mechanisms, which are unique to a particular market, settlement mechanisms could be shared across markets.

  */
package object settlement {

}
