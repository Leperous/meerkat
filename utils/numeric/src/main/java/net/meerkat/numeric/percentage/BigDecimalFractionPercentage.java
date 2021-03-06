package net.meerkat.numeric.percentage;

import net.meerkat.numeric.decimal.BigDecimalFraction;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * @author ollie
 */
public class BigDecimalFractionPercentage extends Percentage {

    private static final long serialVersionUID = 1L;

    public static Percentage of(final long numerator, final long denominator) {
        final BigDecimalFraction fraction = BigDecimalFraction.of(numerator, denominator);
        return fraction.isZero()
                ? zero()
                : new BigDecimalFractionPercentage(fraction);
    }

    public static Percentage of(final Number numerator, final Number denominator) {
        final BigDecimalFraction fraction = BigDecimalFraction.of(numerator, denominator);
        return fraction.isZero()
                ? zero()
                : new BigDecimalFractionPercentage(fraction);
    }

    private final BigDecimalFraction fraction;

    BigDecimalFractionPercentage(final BigDecimalFraction fraction) {
        this.fraction = Objects.requireNonNull(fraction);
    }

    @Override
    public boolean isNegative() {
        return fraction.isNegative();
    }

    @Override
    public BigDecimalFractionPercentage reciprocal() {
        return new BigDecimalFractionPercentage(fraction.reciprocal());
    }

    @Override
    public int intValue() {
        return fraction.intValue();
    }

    @Override
    public long longValue() {
        return fraction.longValue();
    }

    @Override
    public float floatValue() {
        return fraction.floatValue();
    }

    @Override
    public double doubleValue() {
        return fraction.doubleValue();
    }

    @Override
    public Percentage times(Number that, RoundingMode rounding) {
        final BigDecimalFraction fraction = BigDecimalFraction.of(that);
        return fraction.isZero()
                ? zero()
                : new BigDecimalFractionPercentage(this.fraction.times(fraction));
    }

    @Override
    public BigDecimal decimalValue() {
        return fraction.decimalValue();
    }

    @Override
    public BigDecimal decimalValue(final MathContext context) {
        return fraction.decimalValue(context);
    }

}
