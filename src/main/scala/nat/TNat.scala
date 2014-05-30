package nat

import fixpoint._

/**
 * @author Dmytro Starosud <d.starosud@gmail.com>
 */

trait TNat extends Reducible[TNat] {
  type If[T, Z <: T, S[_ <: TNat] <: T] <: T
}

trait Zero extends TNat with ReducibleImpl[TNat, Zero] {
  override type If[T, Z <: T, S[_ <: TNat] <: T] = Z
}

trait Succ[Pred <: TNat] extends TNat with ReducibleImpl[TNat, Succ[Pred]] {
  override type If[T, Z <: T, S[_ <: TNat] <: T] = S[Pred]
}
