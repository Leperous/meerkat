package net.meerkat.instrument.bond;

import net.coljate.list.List;
import net.coljate.list.ListIterator;
import net.meerkat.collections.Iterables;
import net.meerkat.identifier.currency.CurrencyId;
import net.meerkat.identifier.instrument.InstrumentIds;
import net.meerkat.instrument.bond.call.BondCall;
import net.meerkat.instrument.bond.coupon.BondCoupon;
import net.meerkat.instrument.bond.dates.MaturingBondDates;
import net.meerkat.issuer.IssuerId;
import net.meerkat.money.Money;

import java.time.Period;
import java.util.function.Predicate;

/**
 * A bond with fixed and floating elements.
 *
 * @author Ollie
 */
public class VariableRateBond extends StraightBond {

    private final Period couponFrequency;
    private final List<BondCoupon> coupons;

    public VariableRateBond(
            final String name,
            final InstrumentIds identifiers,
            final Money<?> par,
            final MaturingBondDates dates,
            final BondCall call,
            final IssuerId issuer,
            final Period couponFrequency,
            final List<BondCoupon> coupons) {
        super(name, identifiers, par, dates, call, issuer);
        this.couponFrequency = couponFrequency;
        this.coupons = coupons;
    }

    @Override
    public StraightBondCoupons<?> coupons() {
        final CurrencyId commonCurrency = Iterables.requireCommonElement(coupons, BondCoupon::currencyId);
        return new VariableRateBondCoupons<>(couponFrequency, commonCurrency);
    }

    @Override
    public <R> R handleWith(final Bond.Handler<R> handler) {
        return handler.handle(this);
    }

    @Override
    public ExplanationBuilder explain() {
        return super.explain();
    }

    public class VariableRateBondCoupons<C extends CurrencyId>
            extends StraightBondCoupons<BondCoupon> {

        private final C commonCurrency;

        VariableRateBondCoupons(final Period frequency, final C commonCurrency) {
            super(frequency);
            this.commonCurrency = commonCurrency;
        }

        @Override
        public boolean isEmpty() {
            return coupons == null || coupons.isEmpty();
        }

        @Override
        public C currencyId() {
            return commonCurrency;
        }

        @Override
        public VariableRateBondCoupons<C> filter(Predicate<? super BondCoupon> predicate) {
            throw new UnsupportedOperationException(); //TODO
        }

        @Override
        public ListIterator<BondCoupon> iterator() {
            return coupons.iterator();
        }

        @Override
        public BondCoupon last() {
            return coupons.last();
        }

    }

}
