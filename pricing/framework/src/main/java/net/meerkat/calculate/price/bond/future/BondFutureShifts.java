package net.meerkat.calculate.price.bond.future;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;

import javax.annotation.Nonnull;

import net.meerkat.money.Money;
import net.meerkat.calculate.price.bond.BondPrice;
import net.meerkat.calculate.price.bond.BondShifts;
import net.meerkat.calculate.price.shifts.PriceShifts;
import net.meerkat.instrument.interest.repo.rate.RepoRate;
import net.meerkat.identifier.currency.CurrencyId;

/**
 *
 * @author ollie
 */
public interface BondFutureShifts extends PriceShifts {

    /**
     *
     * @param conversionFactor
     * @return
     */
    @Nonnull
    BigDecimal shiftConversionFactor(@Nonnull BigDecimal conversionFactor);

    /**
     *
     * @param <C>
     * @param bondPrice
     * @return
     */
    @Override
    <C extends CurrencyId> Money<C> shift(Money<C> bondPrice);

    /**
     *
     * @return shifts to apply to the cheapest to deliver.
     */
    @Nonnull
    BondShifts bondShifts();

    @Nonnull
    RepoRate shift(RepoRate rate);

    default <C extends CurrencyId> BondPrice.Shiftable<C> shift(final BondPrice.Shiftable<C> price) {
        return price.shift(this.bondShifts());
    }

    static BondFutureShifts none() {
        return NoBondFutureShifts.INSTANCE;
    }

    class NoBondFutureShifts implements BondFutureShifts {

        static final NoBondFutureShifts INSTANCE = new NoBondFutureShifts();

        @Override
        public BondShifts bondShifts() {
            return BondShifts.none();
        }

        @Override
        public <C extends CurrencyId> Money<C> shift(final Money<C> price) {
            return price;
        }

        @Override
        public BigDecimal shiftConversionFactor(final BigDecimal conversionFactor) {
            return conversionFactor;
        }

        @Override
        public RepoRate shift(final RepoRate rate) {
            return rate;
        }

        @Override
        public Map<String, Object> explain() {
            return Collections.emptyMap();
        }

    }

}