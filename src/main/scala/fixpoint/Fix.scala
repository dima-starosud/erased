package fixpoint

/**
 * @author Dmytro Starosud <d.starosud@gmail.com>
 */

trait Reducible {
  type Reduce[TIn <: Reducible, TOut, F[_[_ <: TIn] <: TOut, _ <: TIn] <: TOut, _ <: TIn] <: TOut
}

trait ReducibleImpl extends Reducible {
  override type Reduce[TIn <: Reducible, TOut, F[_[_ <: TIn] <: TOut, _ <: TIn] <: TOut, In <: TIn] =
    F[({type R[In <: TIn] = FixImpl#Fix[TIn, TOut, F, In]})#R, In]
}

trait Fix {
  type Fix[TIn <: Reducible, TOut, F[_[_ <: TIn] <: TOut, _ <: TIn] <: TOut, _ <: TIn] <: TOut
}

trait FixImpl extends Fix {
  override type Fix[TIn <: Reducible, TOut, F[_[_ <: TIn] <: TOut, _ <: TIn] <: TOut, In <: TIn] =
    In#Reduce[TIn, TOut, F, In]
}
