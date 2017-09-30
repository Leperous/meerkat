package net.meerkat.pricing.option;

import javax.annotation.Nonnull;

import net.meerkat.identifier.currency.CurrencyId;
import net.meerkat.instrument.derivative.option.Option;
import net.meerkat.money.Money;
import net.meerkat.money.price.Price;
import net.meerkat.pricing.ShiftablePrice;
import net.meerkat.pricing.shifts.InstrumentShifts;

/**
 *
 * @author Ollie
 */
public interface OptionPrice<C extends CurrencyId> extends Price.Valued<C> {

    /**
     * @return the difference between the underlying price and the {@link Option#strike strike price}.
     * @see <a href="https://en.wikipedia.org/wiki/Intrinsic_value_(finance)">Intrinsic value</a>
     */
    @Nonnull
    Money<C> intrinsicValue();

    /**
     * @return the "uncertainty" premium.
     * @see <a href="https://en.wikipedia.org/wiki/Option_time_value">Time value</a>
     */
    @Nonnull
    Money<C> timeValue();

    @Override
    default Money<C> value() {
        return this.intrinsicValue().plus(this.timeValue());
    }

    @Override
    default EvaluatedOptionPrice<C> evaluate() {
        return new EvaluatedOptionPrice<>(this.intrinsicValue(), this.value());
    }

    interface Shiftable<C extends CurrencyId> extends OptionPrice<C>, ShiftablePrice<C> {

        @Override
        Shiftable<C> shift(InstrumentShifts shifts);

    }

}
