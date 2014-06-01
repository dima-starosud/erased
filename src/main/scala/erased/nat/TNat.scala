package erased
package nat

import fix._

/**
 * @author Dmytro Starosud <d.starosud@gmail.com>
 */

trait TNat extends Reducible {
  type If[T, Z <: T, S[_ <: TNat] <: T] <: T
}

trait Zero extends TNat with ReducibleImpl {
  override type If[T, Z <: T, S[_ <: TNat] <: T] = Z
}

trait Succ[Pred <: TNat] extends TNat with ReducibleImpl {
  override type If[T, Z <: T, S[_ <: TNat] <: T] = S[Pred]
}
