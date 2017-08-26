package net.meerkat.pricing.bond;

import java.time.LocalDate;

import net.coljate.list.List;
import net.meerkat.identifier.currency.CurrencyId;
import net.meerkat.instrument.cash.CashPayment;
import net.meerkat.money.Money;
import net.meerkat.money.fx.ExchangeRate;
import net.ollie.goat.numeric.percentage.Percentage;

/**
 *
 * @author ollie
 */
public class SpotExchangedBondPrice<F extends CurrencyId, C extends CurrencyId>
        implements BondPrice.Shiftable<C> {

    private final BondPrice.Shiftable<F> bondPrice;
    private final ExchangeRate<F, C> rate;

    public SpotExchangedBondPrice(final BondPrice.Shiftable<F> bondPrice, final ExchangeRate<F, C> rate) {
        this.bondPrice = bondPrice;
        this.rate = rate;
    }

    @Override
    public Money<C> par() {
        return rate.convert(bondPrice.par());
    }

    @Override
    public Money<C> clean() {
        return rate.convert(bondPrice.clean());
    }

    @Override
    public List<CashPayment<C>> cleanFlow(final LocalDate start, final LocalDate end) {
        throw new UnsupportedOperationException("Not supported yet."); //TODO
    }

    @Override
    public Money<C> accruedInterest() {
        return rate.convert(bondPrice.accruedInterest());
    }

    @Override
    public Money<C> dirty() {
        return rate.convert(bondPrice.dirty());
    }

    @Override
    public Percentage yieldToMaturity() {
        return bondPrice.yieldToMaturity();
    }

    @Override
    public SpotExchangedBondPrice<F, C> shift(final BondShifts shifts) {
        return new SpotExchangedBondPrice<>(bondPrice.shift(shifts), rate);
    }

}
