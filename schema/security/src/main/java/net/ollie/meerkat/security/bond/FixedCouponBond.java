package net.ollie.meerkat.security.bond;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

import net.ollie.meerkat.numeric.interest.FixedInterestRate;
import net.ollie.meerkat.numeric.money.Money;
import net.ollie.meerkat.security.bond.call.BondCall;
import net.ollie.meerkat.security.bond.coupon.FixedCoupon;
import net.ollie.meerkat.security.bond.dates.MaturingBondDates;

/**
 *
 * @author ollie
 */
@XmlRootElement
public class FixedCouponBond extends StraightBond {

    @XmlElementRef(name = "rate")
    private FixedInterestRate couponRate;

    @XmlElementRef(name = "coupon")
    private Money<?> couponAmount;

    @XmlElement(name = "coupon_date")
    private List<LocalDate> couponDates;

    @Deprecated
    FixedCouponBond() {
    }

    FixedCouponBond(
            final String name,
            final Money<?> par,
            final MaturingBondDates dates,
            final Money<?> couponAmount,
            final List<LocalDate> couponDates,
            final BondCall call) {
        super(name, par, dates, call);
        this.couponAmount = couponAmount;
        this.couponDates = couponDates;
    }

    @Nonnull
    public FixedInterestRate couponRate() {
        return couponRate;
    }

    @Nonnull
    public Money<?> couponAmount() {
        return couponAmount;
    }

    @Override
    public FixedCouponBondCoupons coupons() {
        return new FixedCouponBondCoupons();
    }

    @Override
    public StraightBond strip() {
        return new FixedCouponBond(this.name(), this.par(), this.dates(), this.couponAmount(), Collections.emptyList(), this.call().orElse(null));
    }

    @Override
    public <R> R handleWith(final Bond.Handler<R> handler) {
        return handler.handle(this);
    }

    public class FixedCouponBondCoupons extends StraightBondCoupons<FixedCoupon> {

        @Override
        public FixedCoupon get(final int index) {
            return new FixedCoupon(couponDates.get(index), couponAmount, couponRate);
        }

        @Override
        public int size() {
            return couponDates.size();
        }

    }

}
