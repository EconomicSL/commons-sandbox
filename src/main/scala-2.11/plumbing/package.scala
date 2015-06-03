import environment.TradableLike

/** Provides classes for modeling a payments system.
  *
  * ==Overview==
  * ???
  */
package object plumbing {

  case class PutTradable(tradable: TradableLike)

  case class DeleteTradable(tradable: TradableLike)

}
