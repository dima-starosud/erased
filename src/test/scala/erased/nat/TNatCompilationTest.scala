package erased
package nat

/**
 * @author Dmytro Starosud <d.starosud@gmail.com>
 */

class TNatCompilationTest {
  type One = Succ[Zero]
  type Two = Succ[One]
  type Three = Succ[Two]
  type Four = Succ[Three]

  implicitly[Pred[Zero] =:= Zero]
  implicitly[Pred[One] =:= Zero]
  implicitly[Pred[Two] =:= One]
  implicitly[Pred[Three] =:= Two]

  implicitly[Add[Zero, Zero] =:= Zero]
  implicitly[Add[Zero, One] =:= One]
  implicitly[Add[Zero, Two] =:= Two]
  implicitly[Add[One, Zero] =:= One]
  implicitly[Add[One, One] =:= Two]
  implicitly[Add[One, Two] =:= Three]
  implicitly[Add[Two, Zero] =:= Two]
  implicitly[Add[Two, One] =:= Three]
  implicitly[Add[Two, Two] =:= Four]

  implicitly[Mul[Zero, Zero] =:= Zero]
  implicitly[Mul[Zero, One] =:= Zero]
  implicitly[Mul[Zero, Two] =:= Zero]
  implicitly[Mul[One, Zero] =:= Zero]
  implicitly[Mul[One, One] =:= One]
  implicitly[Mul[One, Two] =:= Two]
  implicitly[Mul[Two, Zero] =:= Zero]
  implicitly[Mul[Two, One] =:= Two]
  implicitly[Mul[Two, Two] =:= Four]

  implicitly[Sub[Zero, Zero] =:= Zero]
  implicitly[Sub[Zero, One] =:= Zero]
  implicitly[Sub[Zero, Two] =:= Zero]
  implicitly[Sub[One, Zero] =:= One]
  implicitly[Sub[One, One] =:= Zero]
  implicitly[Sub[One, Two] =:= Zero]
  implicitly[Sub[Two, Zero] =:= Two]
  implicitly[Sub[Two, One] =:= One]
  implicitly[Sub[Two, Two] =:= Zero]

  trait GenericDefinitionalEquality[N <: TNat, M <: TNat] {
    // rewriting rules which one can rely on
    // useful for reasoning (hello code on code dependencies :-D

    implicitly[Pred[Succ[N]] =:= N]
    implicitly[Pred[Succ[Succ[N]]] =:= Succ[N]]

    // first argument does the job
    implicitly[Add[Succ[N], M] =:= Succ[Add[N, M]]]
    implicitly[Add[Succ[Succ[N]], M] =:= Succ[Succ[Add[N, M]]]]

    // same here
    implicitly[Mul[Succ[N], M] =:= Add[M, Mul[N, M]]]
    implicitly[Mul[Succ[Succ[N]], M] =:= Add[M, Add[M, Mul[N, M]]]]

    // but here second one is main
    implicitly[Sub[N, Succ[M]] =:= Pred[Sub[N, M]]]
    implicitly[Sub[N, Succ[Succ[M]]] =:= Pred[Pred[Sub[N, M]]]]
  }
}
