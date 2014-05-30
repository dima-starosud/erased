package fixpoint

/**
 * @author Dmytro Starosud <d.starosud@gmail.com>
 */

trait Reducible[TIn <: Reducible[TIn]] {
  type Reduce[TOut, F[_[_ <: TIn] <: TOut, _ <: TIn] <: TOut] <: TOut
}

trait Fix {
  type Fix[TIn <: Reducible[TIn], TOut, F[_[_ <: TIn] <: TOut, _ <: TIn] <: TOut, _ <: TIn] <: TOut
}

trait FixImpl extends Fix {
  override type Fix[TIn <: Reducible[TIn], TOut, F[_[_ <: TIn] <: TOut, _ <: TIn] <: TOut, In <: TIn] =
  In#Reduce[TOut, F]
}
