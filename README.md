# erased

Scala type level utilities which anyway will be erased during compilation :-D


## Type level fix point combinator

### Motivation

In Scala there are some restrictions applied to type level functions. Everyone could have seen `illegal cyclic reference` error defining recursive type:

```scala
case object VNil
case class Cons[+H, +T](h: H, t: T)
type Vec[N <: Nat, T] = N#If[VNil.type, ({type F[M <: Nat] = Cons[T, Vec[M, T]]})#F]
// illegal cyclic reference involving type Vec
```

But there is a loophole (please see [apocalisp article](http://apocalisp.wordpress.com/2010/06/08/type-level-programming-in-scala/) for detailed explanation) and it is still possible to define infinite loop in type level.

### Interface and usage

The only thing we need is ability to stop in the right position. And `FixImpl#Fix` (from `Fix.scala`) allows this:

```scala
type Fix[TIn <: Reducible, TOut, F[_[_ <: TIn] <: TOut, _ <: TIn] <: TOut, _ <: TIn] <: TOut

type Vec[N <: TNat, T] = FixImpl#Fix[TNat, Vector[T], ({
  type R[F[_ <: TNat] <: Vector[T], N <: TNat] =
    N#If[Vector[T], VNil.type, ({type R[N <: TNat] = Cons[T, F[N]]})#R]
  })#R, N]
// no illegal cyclic reference
```

### How it works

to be written...
