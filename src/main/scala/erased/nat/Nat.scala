package erased
package nat

/**
 * @author Dmytro Starosud <d.starosud@gmail.com>
 */

sealed trait Nat {
  type T <: TNat
  def apply(m: Motif): m.C[T]
  def lift: NatEx[T]

  def succ: NatEx[Succ[T]]
  def pred: NatEx[Pred[T]]
  def +(m: Nat): NatEx[Add[T, m.T]]
  def -(m: Nat): NatEx[Sub[T, m.T]]
  def *(m: Nat): NatEx[Mul[T, m.T]]
}

sealed trait NatEx[N <: TNat] extends Nat {
  final type T = N
  final def lift: NatEx[T] = this
}

object Nat {
  trait NatOps[N <: TNat] { self: NatEx[N] =>
    def succ: NatEx[Succ[T]] = S(this)

    def pred: NatEx[Pred[T]] = this(new Motif {
      type C[N <: TNat] = NatEx[Pred[N]]
      def zero: NatEx[Zero] = Z
      def succ(n: Nat): NatEx[n.T] = n.lift
    })

    def +(m: Nat): NatEx[Add[T, m.T]] = this(new Motif {
      type C[N <: TNat] = NatEx[Add[N, m.T]]
      def zero: NatEx[m.T] = m.lift
      def succ(n: Nat): NatEx[Succ[Add[n.T, m.T]]] = (n + m).succ
    })

    def -(m: Nat): NatEx[Sub[T, m.T]] = m(new Motif {
      type C[N <: TNat] = NatEx[Sub[T, N]]
      def zero: NatEx[Sub[T, Zero]] = self
      def succ(m: Nat): NatEx[Pred[Sub[T, m.T]]] = (self - m).pred
    })

    def *(m: Nat): NatEx[Mul[T, m.T]] = this(new Motif {
      type C[N <: TNat] = NatEx[Mul[N, m.T]]
      def zero: NatEx[Zero] = Z
      def succ(n: Nat): NatEx[Add[m.T, Mul[n.T, m.T]]] = m + n * m
    })
  }

  final case object Z extends NatEx[Zero] with NatOps[Zero] {
    def apply(m: Motif): m.C[T] = m.zero
  }

  final case class S[P <: TNat](p: NatEx[P]) extends NatEx[Succ[P]] with NatOps[Succ[P]] {
    def apply(m: Motif): m.C[T] = m.succ(p)
  }

  def apply(i: Int): Nat =
    if (i < 0) throw new Exception(s"Cannot build Nat from $i")
    else Seq.iterate[Nat](Z, 1 + i)(_.succ).last
}
