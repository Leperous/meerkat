package net.ollie.meerkat.security.bond;

import java.time.LocalDate;
import java.util.List;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

import net.ollie.goat.currency.Currency;
import net.ollie.goat.money.Money;
import net.ollie.meerkat.identifier.security.SecurityIds;
import net.ollie.meerkat.numeric.interest.FixedInterestRate;
import net.ollie.meerkat.security.bond.call.BondCall;
import net.ollie.meerkat.security.bond.coupon.FixedRateCoupon;
import net.ollie.meerkat.security.bond.dates.MaturingBondDates;
import net.ollie.meerkat.security.fx.CashPayment;

/**
 *
 * @author ollie
 */
@XmlRootElement
public class FixedCouponBond extends StraightBond {

    private static final long serialVersionUID = 1L;

    @XmlElementRef(name = "coupon_rate")
    private FixedInterestRate couponRate;

    @XmlElementRef(name = "coupon_amount")
    private Money<?> couponAmount;

    @XmlElement(name = "coupon_date")
    private List<LocalDate> couponDates;

    @Deprecated
    FixedCouponBond() {
    }

    public FixedCouponBond(
            final String name,
            final SecurityIds identifiers,
            final Money<?> par,
            final MaturingBondDates dates,
            final Money<?> couponAmount,
            final FixedInterestRate couponRate,
            final List<LocalDate> couponDates,
            final BondCall call) {
        super(name, identifiers, par, dates, call);
        this.couponRate = couponRate;
        this.couponAmount = couponAmount;
        this.couponDates = couponDates;
    }

    public CashPayment<?> nominal() {
        return CashPayment.of(this.maturity(), this.par());
    }

    @Nonnull
    public FixedInterestRate couponRate() {
        return couponRate;
    }

    @Nonnull
    public Money<?> couponAmount() {
        return couponAmount;
    }

    public LocalDate maturity() {
        return this.dates().matures();
    }

    @Override
    public FixedCouponBondCoupons<?> coupons() {
        return new FixedCouponBondCoupons<>(couponAmount);
    }

    @Override
    public <R> R handleWith(final Bond.Handler<R> handler) {
        return handler.handle(this);
    }

    public class FixedCouponBondCoupons<C extends Currency> extends StraightBondCoupons<FixedRateCoupon<C>> {

        private final Money<C> couponAmount;

        FixedCouponBondCoupons(final Money<C> couponAmount) {
            this.couponAmount = couponAmount;
        }

        @Override
        public FixedRateCoupon<C> get(final int index) {
            return new FixedRateCoupon<>(couponDates.get(index), couponAmount, couponRate);
        }

        @Override
        public int size() {
            return couponDates.size();
        }

        @Override
        public C currency() {
            return couponAmount.currency();
        }

    }

}
