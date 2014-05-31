import fixpoint._
import nat._

/**
 * @author Dmytro Starosud <d.starosud@gmail.com>
 */
class CompilationTest {
  trait Vector[+H]
  case object VNil extends Vector[Nothing]
  case class Cons[+H, +T <: Vector[H]](h: H, t: T) extends Vector[H]

  type Vec[N <: TNat, T] = FixImpl#Fix[TNat, Vector[T], ({
    type R[F[_ <: TNat] <: Vector[T], N <: TNat] =
      N#If[Vector[T], VNil.type, ({type R[N <: TNat] = Cons[T, F[N]]})#R]
    })#R, N]

  implicitly[VNil.type =:= Vec[Zero, Nothing]]
  implicitly[Cons[String, VNil.type] =:= Vec[Succ[Zero], String]]
  implicitly[Cons[Nothing, Cons[Nothing, VNil.type]] =:= Vec[Succ[Succ[Zero]], Nothing]]
  implicitly[Cons[Any, Cons[Any, Cons[Any, VNil.type]]] =:= Vec[Succ[Succ[Succ[Zero]]], Any]]
}
