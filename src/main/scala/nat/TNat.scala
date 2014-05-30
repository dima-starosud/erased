package nat

import fixpoint.Reducible

/**
 * @author Dmytro Starosud <d.starosud@gmail.com>
 */

trait TNat extends Reducible {
    type If[T, Z <: T, S[_ <: TNat] <: T] <: T
}

trait Zero extends TNat {
    override type If[T, Z <: T, S[_ <: TNat] <: T] = Z
}

trait Succ[Pred <: TNat] extends TNat {
    override type If[T, Z <: T, S[_ <: TNat] <: T] = S[Pred]
}
