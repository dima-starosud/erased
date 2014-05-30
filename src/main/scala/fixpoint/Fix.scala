package fixpoint

/**
 * @author Dmytro Starosud <d.starosud@gmail.com>
 */

trait ReducibleDecl {
    type Reduce[TIn <: ReducibleDecl, TOut, F[_ <: Func[TIn, TOut]] <: Func[TIn, TOut]] <: Func[TIn, TOut]
}

trait Reducible extends ReducibleDecl {
    override type Reduce[TIn <: ReducibleDecl, TOut, F[_ <: Func[TIn, TOut]] <: Func[TIn, TOut]] =
    Fix[TIn, TOut, F]
}

trait FixDecl {
    type Fix[TIn <: ReducibleDecl, TOut, F[_ <: Func[TIn, TOut]] <: Func[TIn, TOut]] <: Func[TIn, TOut]
}

trait FixImpl extends FixDecl {
    override type Fix[TIn <: ReducibleDecl, TOut, F[_ <: Func[TIn, TOut]] <: Func[TIn, TOut]] = Func[TIn, TOut] {
        type Apply[In <: TIn] = In#Reduce[TIn, TOut, F]#Apply[In]
    }
}
