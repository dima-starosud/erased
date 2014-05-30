package fixpoint

/**
 * @author Dmytro Starosud <d.starosud@gmail.com>
 */

trait Reducible[TIn <: Reducible[TIn]] {
  type Reduce[TOut, F[_[_ <: TIn] <: TOut, _ <: TIn] <: TOut] <: TOut
}

trait ReducibleImpl[TIn <: Reducible[TIn], This <: TIn] extends Reducible[TIn] {
  override type Reduce[TOut, F[_[_ <: TIn] <: TOut, _ <: TIn] <: TOut] =
    F[({type R[In <: TIn] = FixImpl#Fix[TIn, TOut, F, In]})#R, This]
}

trait Fix {
  type Fix[TIn <: Reducible[TIn], TOut, F[_[_ <: TIn] <: TOut, _ <: TIn] <: TOut, _ <: TIn] <: TOut
}

trait FixImpl extends Fix {
  override type Fix[TIn <: Reducible[TIn], TOut, F[_[_ <: TIn] <: TOut, _ <: TIn] <: TOut, In <: TIn] =
  In#Reduce[TOut, F]
}
