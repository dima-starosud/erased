# erased

Scala type level utilities which anyway will be erased during compilation :-D


## Type level fix point combinator

### Motivation

In Scala there are some restrictions applied to type level functions. Everyone could have seen `illegal cyclic reference` error defining recursive type:

```scala
case object VNil
case class Cons[+H, +T](h: H, t: T)
type Vec[N <: TNat, T] = N#If[VNil.type, ({type F[M <: TNat] = Cons[T, Vec[M, T]]})#F]
// illegal cyclic reference involving type Vec
```

But there is a loophole (please see [apocalisp article](http://apocalisp.wordpress.com/2010/06/08/type-level-programming-in-scala/) for detailed explanation) and it is still possible to define infinite loop in type level.

### Interface and usage

The only thing we need is ability to stop in the right position. And `Fix` (from `package fix`) allows this:

```scala
type Fix[TIn <: Reducible, TOut, F[_[_ <: TIn] <: TOut, _ <: TIn] <: TOut, _ <: TIn] <: TOut

type Vec[N <: TNat, T] = Fix[TNat, Vector[T], ({
  type R[F[_ <: TNat] <: Vector[T], N <: TNat] =
    N#If[Vector[T], VNil.type, ({type R[N <: TNat] = Cons[T, F[N]]})#R]
  })#R, N]
// no illegal cyclic reference
```

### How it works

The main obstacle of type level fix point combinator implementation was something allowing to stop reducing of a term consisting of types (in this case it's more expansion than reduction).

During the computation (reduction) there is always the main object doing the job. And this very object initiates reduction. In samples above such a role is played by `N <: TNat`. So all we need is tie together fix point expansion and initiator object reduction. We will do it in the following way:

1. Create trait `Reducible` with abstract `type Reduce[...]`;
2. Create trait `ReducibleImpl` extending `Reducible` with "implementation" of `type Reduce[...]`;
3. Make type level type (like `TNat` in samples above) extending `Reducible` (see `nat/TNat.scala`), that is it's "reducible" but abstract, so cannot "reduce";
4. Make all the "instances" of that type level type (like `Zero` and `Succ`) extending `ReducibleImpl`.

That's it. When we have some type level term involving `Reducible` (but not `ReducibleImpl`) we do nothing, because `Reduce` type is abstract. But as soon as we are passed concrete types extending `ReducibleImpl` (where `Reduce` is able to do the job) computation is starting. And is stopped again when there is no more `ReducibleImpl` to proceed.

So the rule is "make reduction step, when your main object is able do its own job".
