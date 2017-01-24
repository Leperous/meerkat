package net.meerkat.instrument.bond.swap;

import java.time.LocalDate;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlTransient;

import net.ollie.goat.temporal.date.HasDate;
import net.meerkat.money.interest.HasInterestRateId;
import net.meerkat.money.interest.InterestRateId;
import net.meerkat.instrument.bond.coupon.FixedRateCoupon;
import net.meerkat.instrument.derivative.swap.SwapLeg;

/**
 *
 * @author Ollie
 */
@XmlTransient
public class BondAssetSwapLeg implements SwapLeg, HasDate, HasInterestRateId {

    private final FixedRateCoupon coupon;
    private final InterestRateId rateId;

    public BondAssetSwapLeg(final FixedRateCoupon coupon, InterestRateId rateId) {
        this.coupon = coupon;
        this.rateId = rateId;
    }

    @Override
    public LocalDate date() {
        return coupon.date();
    }

    @Nonnull
    public FixedRateCoupon coupon() {
        return coupon;
    }

    @Nonnull
    @Override
    public InterestRateId interestRateId() {
        return rateId;
    }

}
