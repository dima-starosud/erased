package erased
package nat

/**
 * @author Dmytro Starosud <d.starosud@gmail.com>
 */

trait Motif {
  type C[_ <: TNat]
  def zero: C[Zero]
  def succ(n: Nat): C[Succ[n.T]]
  final def apply(n: Nat): C[n.T] = n(this)
}

trait ConstMotif { this: Motif =>
  type T
  type C[_] = T
}

trait DoubleMotif { self =>
  type C[_ <: TNat, _ <: TNat]
  def zz: C[Zero, Zero]
  def zs(m: Nat): C[Zero, Succ[m.T]]
  def sz(n: Nat): C[Succ[n.T], Zero]
  def ss(n: Nat, m: Nat): C[Succ[n.T], Succ[m.T]]

  final def apply(n: Nat, m: Nat): C[n.T, m.T] =
    n(new Motif {
      type C[N <: TNat] = self.C[N, m.T]
      def zero: self.C[Zero, m.T] =
	m(new Motif {
	  type C[M <: TNat] = self.C[Zero, M]
	  def zero: self.C[Zero, Zero] = zz
	  def succ(m: Nat): self.C[Zero, Succ[m.T]] = zs(m)
	})
      def succ(n: Nat): self.C[Succ[n.T], m.T] =
	m(new Motif {
	  type C[M <: TNat] = self.C[Succ[n.T], M]
	  def zero: self.C[Succ[n.T], Zero] = sz(n)
	  def succ(m: Nat): self.C[Succ[n.T], Succ[m.T]] = ss(n, m)
	})
    })
}

class IterateMotif[A] extends Motif with ConstMotif {
  type T = (A => A) => A => A
  def zero: T = _ => a => a
  def succ(n: Nat): T = f => n(this)(f) andThen f
}
