/**
 * @author Dmytro Starosud <d.starosud@gmail.com>
 */

package object fixpoint extends FixImpl {
    type Func[TIn, TOut] = {
        type Apply[_ <: TIn] <: TOut
    }
}
