package net.meerkat.instrument.interest.future.stir;

import net.meerkat.identifier.currency.GBP;
import net.meerkat.identifier.instrument.InstrumentIds;
import net.meerkat.instrument.NamedInstrument;
import net.meerkat.money.Money;
import net.meerkat.temporal.date.Periods;
import net.meerkat.temporal.date.months.Months;

import java.time.Period;

/**
 *
 * @author ollie
 */
public class ShortSterlingFutureContract
        extends NamedInstrument
        implements ShortTermInterestRateFutureContract<GBP> {

    private static final Money<GBP> NOTIONAL = Money.of(GBP.GBP, 500_000);

    public ShortSterlingFutureContract(final InstrumentIds ids) {
        this("Short Sterling", ids);
    }

    public ShortSterlingFutureContract(final String name, final InstrumentIds ids) {
        super(name, ids);
    }

    @Override
    public Money<GBP> notional() {
        return NOTIONAL;
    }

    @Override
    public Period term() {
        return Periods.THREE_MONTHS;
    }

    @Override
    public Months deliveryMonths() {
        return MAR_JUN_SEP_DEC;
    }

}
