package net.ollie.meerkat.security.bond.swap;

import java.time.LocalDate;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlTransient;

import net.ollie.goat.date.HasDate;
import net.ollie.meerkat.numeric.interest.HasInterestRateId;
import net.ollie.meerkat.numeric.interest.InterestRateId;
import net.ollie.meerkat.security.bond.coupon.FixedCoupon;
import net.ollie.meerkat.security.derivative.swap.SwapLeg;

/**
 *
 * @author Ollie
 */
@XmlTransient
public class BondAssetSwapLeg implements SwapLeg, HasDate, HasInterestRateId {

    private final FixedCoupon coupon;
    private final InterestRateId rateId;

    public BondAssetSwapLeg(final FixedCoupon coupon, InterestRateId rateId) {
        this.coupon = coupon;
        this.rateId = rateId;
    }

    @Override
    public LocalDate date() {
        return coupon.date();
    }

    @Nonnull
    public FixedCoupon coupon() {
        return coupon;
    }

    @Nonnull
    @Override
    public InterestRateId interestRateId() {
        return rateId;
    }

}