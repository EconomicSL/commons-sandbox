package plumbing.contracts

/** Classes for constructing composable contracts.
  *
  * ==Overview==
  * Instead of defining a fixed catalogue of tradable financial contracts, our
  * approach is to instead define a small set of primitive contracts that can
  * be used as "building blocks" to construct more complicated contracts.
  *
  * We envision that:
  *
  * 1. model builders will use these primitive contracts to construct commonly
  * encountered financial contracts such as stocks, bonds, deposits, secured
  * and unsecured loans, etc.
  * 2. actors within a model will use these primitive contracts to innovate new,
  * unforeseen financial contracts.
  *
  * ===Composable contracts===
  * Implement the approach from [[https://lexifi.com/files/resources/MLFiPaper.pdf Peyton-Jones et al (2000)]].
  *
  */
package object primitives {

}
