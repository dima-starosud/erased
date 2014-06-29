package erased

import fix._
import nat._

import org.junit.Assert._
import org.junit.Test

/**
 * @author Dmytro Starosud <d.starosud@gmail.com>
 */

object VectorTest {
  sealed trait Vector[+A] {
    def ::[H >: A](h: H): Cons[H, this.type] = Cons(h, this)
    def toList: List[A]
    final override def toString: String = toList.mkString("Vector(", ", ", ")")
  }

  final case object VNil extends Vector[Nothing] {
    def toList = List()
  }

  final case class Cons[+H, +T <: Vector[H]](h: H, t: T) extends Vector[H] {
    def toList = h :: t.toList
  }
}

@Test
class VectorTest {
  import VectorTest._

  type Vec[N <: TNat, T] = Iterate[N, Vector[T], VNil.type,
    ({type S[V <: Vector[T]] = Cons[T, V]})#S]

  object Vec {
    def apply[T](n: Nat): List[T] => Option[Vec[n.T, T]] =
      n(new Motif {
        type C[N <: TNat] = List[T] => Option[Vec[N, T]]
        def zero: List[T] => Option[VNil.type] = _ match {
	  case Nil => Some(VNil)
	  case _ => None
        }
        def succ(n: Nat): List[T] => Option[Cons[T, Vec[n.T, T]]] = xs => for {
	  vh :: t <- Option(xs)
	  vt <- n(this)(t)
        } yield Cons(vh, vt)
      })
  }

  type One = Succ[Zero]
  type Two = Succ[One]
  type Three = Succ[Two]

  implicitly[VNil.type =:= Vec[Zero, Nothing]]
  implicitly[Cons[String, VNil.type] =:= Vec[One, String]]
  implicitly[Cons[Nothing, Cons[Nothing, VNil.type]] =:= Vec[Two, Nothing]]
  implicitly[Cons[Any, Cons[Any, Cons[Any, VNil.type]]] =:= Vec[Three, Any]]

  @Test
  def simpleTest() {
    val n = Nat(3)
    assertEquals(Vec(n)(List(1, 2)), None)
    assertEquals(Vec(n)(List(1, 2, 3)), Some(1 :: 2 :: 3 :: VNil))
    assertEquals(Vec(n)(List(1, 2, 3, 4)), None)
  }
}
