package net.meerkat.pricing.bond;

import java.time.LocalDate;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

import net.coljate.list.List;
import net.meerkat.Explainable.ExplanationBuilder;
import net.meerkat.identifier.currency.CurrencyId;
import net.meerkat.instrument.cash.CashPayment;
import net.meerkat.money.Money;
import net.meerkat.money.price.Price;
import net.meerkat.pricing.ShiftablePrice;
import net.meerkat.pricing.bond.shifts.BondShifts;
import net.meerkat.sensitivity.bond.BondSensitivities;
import net.ollie.goat.numeric.percentage.FractionalPercentage;
import net.ollie.goat.numeric.percentage.Percentage;
import net.ollie.goat.temporal.date.interim.CompleteInterval;
import net.meerkat.pricing.shifts.InstrumentShifts;

/**
 *
 * @author ollie
 * @see BondSensitivities
 */
public interface BondPrice<C extends CurrencyId>
        extends Price<C> {

    @Nonnull
    Money<C> par();

    @Nonnull
    Money<C> clean();

    @Nonnull
    Money<C> dirty();

    @Override
    default C currencyId() {
        return this.clean().currencyId();
    }

    @Nonnull
    default Percentage cleanPercent() {
        return FractionalPercentage.of(this.par().amount(), this.clean().amount());
    }

    @Nonnull
    default Percentage dirtyPercent() {
        return FractionalPercentage.of(this.par().amount(), this.dirty().amount());
    }

    default boolean isPremium() {
        return this.dirty().compareTo(this.par()) > 0;
    }

    @Nonnull
    default Money<C> accruedInterest() {
        return this.dirty().minus(this.clean());
    }

    @Nonnull
    Percentage yieldToMaturity();

    @Nonnull
    default Money<C> marketValue(final Number quantity) {
        return this.dirty().times(quantity);
    }

    @Override
    default EvaluatedBondPrice<C> evaluate() {
        return new EvaluatedBondPrice<>(this.clean(), this.dirty(), this.par(), this.yieldToMaturity());
    }

    @Override
    default ExplanationBuilder explain() {
        return this.explanationBuilder()
                .put("clean", this.clean())
                .put("dirty", this.dirty())
                .put("par", this.par());
    }

    interface Shiftable<C extends CurrencyId>
            extends BondPrice<C>, ShiftablePrice<C> {

        @Override
        default BondPrice.Shiftable<C> shift(final InstrumentShifts shifts) {
            return this.shift(BondShifts.cast(shifts));
        }

        @CheckReturnValue
        BondPrice.Shiftable<C> shift(BondShifts shifts);

        @Nonnull
        default List<CashPayment<C>> cleanFlow(final CompleteInterval interval) {
            return this.cleanFlow(interval.startInclusive(), interval.endExclusive());
        }

        List<CashPayment<C>> cleanFlow(LocalDate starInclusive, LocalDate endExclusive);

    }

}
