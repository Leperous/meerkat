package net.meerkat.money.interest.curve;

import java.time.Period;
import java.util.Map.Entry;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

import net.ollie.goat.collection.Iterables;
import net.ollie.goat.numeric.interpolation.Interpolator;
import net.ollie.goat.numeric.manifold.Curve;
import net.ollie.goat.numeric.percentage.Percentage;

/**
 *
 * @author Ollie
 */
public interface YieldCurve<K> extends Curve<K, Percentage> {

    @Nonnull
    @CheckReturnValue
    YieldCurve<K> plus(@Nonnull Percentage bump);

    default boolean isFlat() {
        return Iterables.allEqual(this.yAxis());
    }

    default boolean isNormal() {
        return Iterables.isIncreasing(this.yAxis());
    }

    Entry<K, Percentage> interpolate(Period tenor, Interpolator<K, Percentage> interpolation);

    default Percentage interpolateRate(final Period tenor, final Interpolator<K, Percentage> interpolation) {
        return this.interpolate(tenor, interpolation).getValue();
    }

}
