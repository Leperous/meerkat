package net.meerkat.pricing;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

import net.meerkat.identifier.currency.CurrencyId;
import net.meerkat.money.price.Price;
import net.meerkat.pricing.shifts.InstrumentPriceShifts;

/**
 *
 * @author ollie
 */
public interface ShiftablePrice<C extends CurrencyId>
        extends Price<C> {

    @Nonnull
    @CheckReturnValue
    ShiftablePrice<C> shift(@Nonnull InstrumentPriceShifts shifts);

}
