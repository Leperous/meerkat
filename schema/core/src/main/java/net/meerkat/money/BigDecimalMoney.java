package net.meerkat.money;

import net.meerkat.identifier.currency.CurrencyId;
import net.meerkat.numeric.decimal.BigDecimals;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Objects;

/**
 *
 * @author Ollie
 */
public class BigDecimalMoney<C extends CurrencyId>
        implements Money<C> {

    private static final long serialVersionUID = 1L;

    public static <C extends CurrencyId> BigDecimalMoney<C> valueOf(final Money<C> money) {
        return money instanceof BigDecimalMoney
                ? (BigDecimalMoney<C>) money
                : valueOf(money.currencyId(), money.value());
    }

    public static <C extends CurrencyId> BigDecimalMoney<C> valueOf(final C currency, final Number amount) {
        return new BigDecimalMoney<>(currency, BigDecimals.toBigDecimal(amount));
    }

    public static <C extends CurrencyId> BigDecimalMoney<C> valueOf(final C currency, final double amount) {
        return new BigDecimalMoney<>(currency, BigDecimal.valueOf(amount));
    }

    private final C currency;
    private final BigDecimal amount;

    public BigDecimalMoney(@Nonnull final C currency, @Nonnull final BigDecimal amount) {
        this.currency = Objects.requireNonNull(currency, "currency");
        this.amount = Objects.requireNonNull(amount, "value");
    }

    @Override
    public C currencyId() {
        return currency;
    }

    @Override
    public BigDecimal value() {
        return amount;
    }

    @Override
    public boolean isZero() {
        return amount.signum() == 0;
    }

    @Override
    public Money<C> plus(final Money<C> that) {
        return that.isZero()
                ? this
                : new BigDecimalMoney<>(currency, amount.add(that.decimalValue()));
    }

    @Override
    public BigDecimalMoney<C> times(final Number n) {
        return new BigDecimalMoney<>(currency, amount.multiply(BigDecimals.toBigDecimal(n)));
    }

    @Override
    @Deprecated
    public Money<C> times(final Number that, final RoundingMode rounding) {
        return this.times(that);
    }

    @Override
    public BigDecimalMoney<C> negate() {
        return new BigDecimalMoney<>(currency, amount.negate());
    }

    @Override
    public BigDecimal decimalValue(final MathContext context) {
        return amount.round(context);
    }

    @Override
    public BigDecimalMoney<C> toDecimal() {
        return this;
    }

    @Override
    public String toString() {
        return this.toString(currency.format());
    }

    @Override
    public boolean equals(final Object obj) {
        return obj instanceof Money
                && Money.valuesEqual(this, (Money) obj);
    }

    @Override
    public int hashCode() {
        return Money.hashCode(this);
    }


}
