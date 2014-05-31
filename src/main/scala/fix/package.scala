
/**
 * @author Dmytro Starosud <d.starosud@gmail.com>
 */

package object fix {
  type Fix[TIn <: Reducible, TOut, F[_[_ <: TIn] <: TOut, _ <: TIn] <: TOut, In <: TIn] =
    In#Reduce[TIn, TOut, F, In]
}
