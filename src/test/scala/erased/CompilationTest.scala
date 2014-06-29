package erased

import fix._
import nat._

/**
 * @author Dmytro Starosud <d.starosud@gmail.com>
 */

class CompilationTest {
  trait Vector[+H]
  case object VNil extends Vector[Nothing]
  case class Cons[+H, +T <: Vector[H]](h: H, t: T) extends Vector[H]

  type Vec[N <: TNat, H] = Iterate[N, Vector[H], VNil.type, ({
    type R[T <: Vector[H]] = Cons[H, T]
  })#R]

  type One = Succ[Zero]
  type Two = Succ[One]
  type Three = Succ[Two]

  implicitly[VNil.type =:= Vec[Zero, Nothing]]
  implicitly[Cons[String, VNil.type] =:= Vec[One, String]]
  implicitly[Cons[Nothing, Cons[Nothing, VNil.type]] =:= Vec[Two, Nothing]]
  implicitly[Cons[Any, Cons[Any, Cons[Any, VNil.type]]] =:= Vec[Three, Any]]
}
