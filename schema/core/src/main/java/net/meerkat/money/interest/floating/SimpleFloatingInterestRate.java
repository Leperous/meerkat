package net.meerkat.money.interest.floating;

import net.meerkat.identifier.currency.CurrencyId;
import net.meerkat.money.Money;
import net.meerkat.money.interest.curve.DateYieldCurve;
import net.meerkat.money.interest.fixed.SimpleFixedInterestRate;
import net.meerkat.money.interest.interpolation.InterestRateInterpolator;
import net.meerkat.numeric.percentage.Percentage;
import net.meerkat.temporal.date.count.DateArithmetic;
import net.meerkat.temporal.date.years.Years;

import java.time.LocalDate;

/**
 *
 * @author Ollie
 */
public class SimpleFloatingInterestRate extends FloatingInterestRate {

    private final DateYieldCurve curve;

    public SimpleFloatingInterestRate(
            final LocalDate spot,
            final DateYieldCurve curve,
            final DateArithmetic accrual) {
        super(spot, accrual);
        this.curve = curve;
    }

    @Override
    public SimpleFloatingInterestRate plus(final Percentage bump) {
        return bump.isZero()
                ? this
                : new SimpleFloatingInterestRate(this.referenceDate(), curve.plus(bump), this.dateArithmetic());
    }

    @Override
    public SimpleFloatingInterestRate times(final Percentage bump) {
        return new SimpleFloatingInterestRate(this.referenceDate(), curve.times(bump), this.dateArithmetic());
    }

    @Override
    public Percentage spotRate(final LocalDate end, final InterestRateInterpolator interpolator) {
        return curve.get(end, interpolator);
    }

    @Override
    public Percentage forwardRate(final LocalDate start, final LocalDate end, final InterestRateInterpolator interpolator) {
        final Percentage r1 = this.spotRate(start, interpolator);
        final Percentage r2 = this.spotRate(end, interpolator);
        final Years d1 = this.yearsUntil(start);
        final Years d2 = this.yearsUntil(end);
        //([(1+r2d2)/(1+r1d1)]-1)/(d2-d1)
        throw new UnsupportedOperationException(); //TODO
    }

    @Override
    protected <C extends CurrencyId> Money<C> accrue(final Money<C> money, final Percentage forwardRate, final LocalDate start, final LocalDate end) {
        return SimpleFixedInterestRate.accrue(money, forwardRate, this.dateArithmetic().yearsBetween(start, end));
    }

}
