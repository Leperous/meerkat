package net.meerkat.numeric.manifold.derivative;

import net.meerkat.numeric.manifold.Curve;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.util.NavigableMap;

/**
 * @param <X>  x-axis type
 * @param <Y>  y-axis type
 * @param <DY> y'-axis type
 * @see FiniteDifference
 */
public interface Differentiation<X, Y, DY> {

    @Nonnull
    default Curve<X, DY> differentiate(@Nonnull final Curve<X, Y> curve) {
        return Curve.of(this.differentiate(curve.toMap()));
    }

    @Nonnull
    NavigableMap<X, DY> differentiate(@Nonnull NavigableMap<X, Y> map);

    interface HigherOrder<X, Y> extends Differentiation<X, Y, Y> {

        default Curve<X, Y> differentiate(final Curve<X, Y> curve, @Nonnegative final int order) {
            if (order < 0) {
                throw new IllegalArgumentException("Differentiation order must be non-negative but was " + order);
            }
            Curve<X, Y> differentiated = curve;
            for (int i = 0; i < order; i++) {
                differentiated = this.differentiate(differentiated);
            }
            return differentiated;
        }

    }

}
