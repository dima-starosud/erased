package nat

import fixpoint._

/**
 * @author Dmytro Starosud <d.starosud@gmail.com>
 */

trait TNat extends Reducible[TNat] {
  type If[T, Z <: T, S[_ <: TNat] <: T] <: T
}

trait Zero extends TNat {
  override type If[T, Z <: T, S[_ <: TNat] <: T] = Z
  override type Reduce[TOut, F[_[_ <: TNat] <: TOut, _ <: TNat] <: TOut] =
  F[({type R[N <: TNat] = FixImpl#Fix[TNat, TOut, F, N]})#R, Zero]
}

trait Succ[Pred <: TNat] extends TNat {
  override type If[T, Z <: T, S[_ <: TNat] <: T] = S[Pred]
  override type Reduce[TOut, F[_[_ <: TNat] <: TOut, _ <: TNat] <: TOut] =
  F[({type R[N <: TNat] = FixImpl#Fix[TNat, TOut, F, N]})#R, Succ[Pred]]
}
