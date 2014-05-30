import fixpoint._
import nat._

/**
 * @author Dmytro Starosud <d.starosud@gmail.com>
 */
class CompilationTest {

    trait Vector[+H]

    case object VNil extends Vector[Nothing]

    case class Cons[+H, +T <: Vector[H]](h: H, t: T) extends Vector[H]

    type Vec[N <: TNat, T] = Fix[TNat, Vector[T], ({
        type FT = Func[TNat, Vector[T]]
        type R[F <: FT] = FT {
            type Apply[N <: TNat] = N#If[Vector[T], VNil.type, ({
                type R[N <: TNat] = Cons[T, F#Apply[N]]
            })#R]
        }
    })#R]#Apply[N]

    implicitly[VNil.type =:= Vec[Zero, Any]]

    //    implicitly[VNil.type =:= Vec[Zero, Nothing]]

    //    implicitly[VNil.type =:= Vec[Succ[Zero], Nothing]]
}
