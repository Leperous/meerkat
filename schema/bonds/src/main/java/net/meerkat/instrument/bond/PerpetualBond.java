package net.meerkat.instrument.bond;

import java.time.LocalDate;
import java.util.Optional;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

import net.meerkat.money.Money;
import net.meerkat.money.interest.fixed.FixedInterestRate;
import net.meerkat.instrument.bond.coupon.BondCoupons;
import net.meerkat.instrument.bond.coupon.FixedCoupon;
import net.meerkat.instrument.bond.dates.PerpetualBondDates;
import net.meerkat.utils.collections.sequence.FiniteSequence;
import net.meerkat.identifier.currency.CurrencyId;
import net.meerkat.identifier.currency.HasCurrencyId;
import net.meerkat.identifier.instrument.InstrumentIds;
import net.meerkat.instrument.bond.call.BondCall;
import net.meerkat.issuer.IssuerId;

/**
 *
 * @author ollie
 */
public class PerpetualBond extends AbstractBond {

    private static final long serialVersionUID = 1L;

    private final int yearlyFrequency;
    private final Money<?> couponAmount;
    private final FixedInterestRate couponRate;
    private final PerpetualBondDates dates;

    private transient PerpetualBondCoupons<?> coupons;

    public PerpetualBond(int yearlyFrequency, Money<?> couponAmount, FixedInterestRate couponRate, PerpetualBondDates dates, String name, InstrumentIds identifiers, Money<?> par, BondCall call, IssuerId issuer) {
        super(name, identifiers, par, call, issuer);
        this.yearlyFrequency = yearlyFrequency;
        this.couponAmount = couponAmount;
        this.couponRate = couponRate;
        this.dates = dates;
    }

    @Override
    public PerpetualBondCoupons<?> coupons() {
        return coupons == null ? (coupons = new PerpetualBondCoupons<>(couponAmount)) : coupons;
    }

    @Override
    public PerpetualBondDates dates() {
        return dates;
    }

    @Override
    @Deprecated
    public boolean isZeroCoupon() {
        return false;
    }

    @Override
    public <R> R handleWith(final Bond.Handler<R> handler) {
        return handler.handle(this);
    }

    public class PerpetualBondCoupons<C extends CurrencyId>
            implements BondCoupons<FixedCoupon<?>>, HasCurrencyId {

        private final Money<C> coupon;

        PerpetualBondCoupons(final Money<C> coupon) {
            this.coupon = coupon;
        }

        public FixedInterestRate yearlyRate() {
            return couponRate;
        }

        public Money<?> yearlyAmount() {
            return couponAmount;
        }

        @Override
        public boolean hasFloatingRateCoupon() {
            return false;
        }

        public int yearlyFrequency() {
            return yearlyFrequency;
        }

        @Override
        public FixedCoupon<?> first() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        @Deprecated
        public boolean isEmpty() {
            return false;
        }

        @Override
        public PerpetualBondCoupons<C> onOrAfter(final LocalDate time) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public FiniteSequence<FixedCoupon<?>> between(LocalDate startInclusive, LocalDate endExclusive) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public FixedCoupon<?> prior(final LocalDate current) {
            throw new UnsupportedOperationException(); //TODO
        }

        @Override
        public Optional<Long> count() {
            return Optional.empty();
        }

        @Override
        public C currencyId() {
            return coupon.currencyId();
        }

    }

}
