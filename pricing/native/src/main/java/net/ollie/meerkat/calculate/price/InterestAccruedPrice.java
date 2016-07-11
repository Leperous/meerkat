package net.ollie.meerkat.calculate.price;

import net.ollie.goat.money.Money;
import net.ollie.goat.money.currency.Currency;
import net.ollie.goat.money.fx.ExchangeRate;
import net.ollie.goat.money.interest.InterestRate;
import net.ollie.goat.temporal.date.interim.CompleteInterval;
import net.ollie.meerkat.calculate.price.shifts.ExchangeRateShifts;
import net.ollie.meerkat.calculate.price.shifts.InterestRateShifts;
import net.ollie.meerkat.calculate.price.shifts.SecurityShifts;
import net.ollie.meerkat.security.SecurityDefinition;

/**
 *
 * @author ollie
 */
public class InterestAccruedPrice<F extends Currency, C extends Currency>
        implements ShiftableSecurityPrice<C> {

    private final SecurityDefinition security;
    private final Money<F> notional;
    private final InterestRate interestRate;
    private final ExchangeRate<F, C> fxRate;
    private final CompleteInterval period;
    private final SecurityShifts shifts;

    public InterestAccruedPrice(
            final SecurityDefinition security,
            final Money<F> notional,
            final InterestRate interestRate,
            final ExchangeRate<F, C> fxRate,
            final CompleteInterval period,
            final SecurityShifts shifts) {
        this.security = security;
        this.notional = notional;
        this.interestRate = interestRate;
        this.fxRate = fxRate;
        this.period = period;
        this.shifts = shifts;
    }

    @Override
    public ShiftableSecurityPrice<C> shift(final SecurityShifts shifts) throws InvalidShiftTypeException {
        return new InterestAccruedPrice<>(security, notional, interestRate, fxRate, period, shifts);
    }

    InterestRate interestRate() {
        return shifts instanceof InterestRateShifts
                ? ((InterestRateShifts) shifts).shift(interestRate)
                : interestRate;
    }

    ExchangeRate<F, C> fxRate() {
        return shifts instanceof ExchangeRateShifts
                ? ((ExchangeRateShifts) shifts).shift(fxRate)
                : fxRate;
    }

    @Override
    public Money<C> clean() {
        return this.fxRate().convert(notional);
    }

    @Override
    public Money<C> dirty() {
        final Money<F> accrued = this.interestRate().accrue(notional, period);
        return this.fxRate().convert(accrued);
    }

    @Override
    public ExplanationBuilder explain() {
        return ShiftableSecurityPrice.super.explain()
                .put("security", security)
                .put("shifts", shifts)
                .put("base interest rate", interestRate)
                .put("base fx rate", fxRate)
                .putIf(shifts != null, "shifted interest rate", this.interestRate())
                .putIf(shifts != null, "shifted fx rate", this.fxRate());
    }

}
