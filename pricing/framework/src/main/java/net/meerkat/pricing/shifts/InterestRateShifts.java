package net.meerkat.pricing.shifts;

import javax.annotation.Nonnull;

import net.meerkat.money.interest.InterestRate;
import net.meerkat.money.interest.fixed.FixedInterestRate;
import net.ollie.goat.numeric.percentage.Percentage;

/**
 *
 * @author Ollie
 */
public interface InterestRateShifts extends SecurityShifts {

    @Nonnull
    InterestRate shift(@Nonnull InterestRate rate);

    static InterestRateShifts none() {
        return NoInterestRateShifts.INSTANCE;
    }

    static InterestRateShifts absolute(@Nonnull final Percentage shift) {
        return shift.isZero()
                ? none()
                : new AbsoluteInterestRateShifts(shift);
    }

    static InterestRateShifts relative(@Nonnull final Percentage shift) {
        return new RelativeInterestRateShifts(shift);
    }

    static InterestRateShifts cast(final SecurityShifts shifts) {
        return shifts.as(InterestRateShifts.class).orElseGet(InterestRateShifts::none);
    }

    interface InterestRateShifter {

        default InterestRate shift(final InterestRate rate, final InterestRateShifts shifts) {
            return shifts.shift(rate);
        }

        default FixedInterestRate shift(final FixedInterestRate rate, final InterestRateShifts shifts) {
            return (FixedInterestRate) shifts.shift(rate);
        }

    }

}
