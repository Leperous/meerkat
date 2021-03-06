package net.meerkat.money.interest.curve;

import net.meerkat.collections.Iterables;
import net.meerkat.money.interest.floating.FloatingInterestRate;
import net.meerkat.numeric.interpolation.Interpolator;
import net.meerkat.numeric.manifold.Curve;
import net.meerkat.numeric.percentage.Percentage;
import net.meerkat.temporal.date.count.DateArithmetic;
import net.meerkat.temporal.date.years.Years;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Ollie
 */
public interface YieldCurve<K> extends Curve<K, Percentage> {

    Entry<K, Percentage> interpolate(Tenor tenor, Interpolator<K, Percentage> interpolation);

    default Percentage interpolateRate(final Tenor tenor, final Interpolator<K, Percentage> interpolation) {
        return this.interpolate(tenor, interpolation).getValue();
    }

    @Nonnull
    @CheckReturnValue
    YieldCurve<K> plus(@Nonnull Percentage bump);

    @CheckReturnValue
    YieldCurve<K> plus(Curve<K, Percentage> that, Interpolator<K, Percentage> interpolator);

    DateYieldCurve resolve(LocalDate spotDate);

    default FloatingInterestRate toInterestRate(LocalDate referenceDate, DateArithmetic dateArithmetic) {
        return this.resolve(referenceDate).toInterestRate(dateArithmetic);
    }

    default boolean isFlat() {
        return Iterables.allEqual(this.yAxis());
    }

    default boolean isNormal() {
        return Iterables.isIncreasing(this.yAxis());
    }

    static DateYieldCurve ofDates(final LocalDate spot, final Map<LocalDate, Percentage> curve) {
        return new DateYieldCurve(spot, curve);
    }

    static YearsYieldCurve ofYears(final Map<Years, Percentage> curve) {
        return new YearsYieldCurve(curve);
    }

    static TenorYieldCurve ofTenors(final Map<Tenor, Percentage> curve) {
        return new TenorYieldCurve(curve);
    }

}
