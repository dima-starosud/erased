package erased
package nat

import fix._

/**
 * @author Dmytro Starosud <d.starosud@gmail.com>
 */

sealed trait TNat extends Reducible {
  type Match[T, Z <: T, S[_ <: TNat] <: T] <: T
}

sealed trait Zero extends TNat with ReducibleImpl {
  override type Match[T, Z <: T, S[_ <: TNat] <: T] = Z
}

sealed trait Succ[Pred <: TNat] extends TNat with ReducibleImpl {
  override type Match[T, Z <: T, S[_ <: TNat] <: T] = S[Pred]
}

trait TNatOps {
  type Iterate[N <: TNat, T, Z <: T, S[_ <: T] <: T] =
    Fix[TNat, T, ({
      type R[F[_ <: TNat] <: T, N <: TNat] =
        N#Match[T, Z, ({type R[N <: TNat] = S[F[N]]})#R]
    })#R, N]

  type Add[N <: TNat, M <: TNat] = Iterate[N, TNat, M, Succ]
  type Mul[N <: TNat, M <: TNat] = Iterate[N, TNat, Zero, ({type R[P <: TNat] = Add[M, P]})#R]
  type Pred[N <: TNat] = N#Match[TNat, Zero, ({type R[P <: TNat] = P})#R]
  type Sub[N <: TNat, M <: TNat] = Iterate[M, TNat, N, Pred]
}
