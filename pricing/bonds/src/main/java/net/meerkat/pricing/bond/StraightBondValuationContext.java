package net.meerkat.pricing.bond;

import net.coljate.list.List;
import net.meerkat.identifier.currency.CurrencyId;
import net.meerkat.identifier.currency.HasCurrencyId;
import net.meerkat.instrument.bond.StraightBond;
import net.meerkat.instrument.bond.coupon.BondCoupon;
import net.meerkat.money.Money;
import net.meerkat.money.interest.InterestRate;
import net.meerkat.money.interest.interpolation.InterestRateInterpolator;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.time.LocalDate;

/**
 * Pricing helper.
 *
 * @author ollie
 */
public interface StraightBondValuationContext<C extends CurrencyId> extends HasCurrencyId {

    @Override
    C currencyId();

    @Nonnull
    LocalDate valueDate();

    @Nonnull
    StraightBond bond();

    default Money<?> par() {
        return this.bond().par();
    }

    default List<? extends BondCoupon> unpaidCoupons() {
        return this.bond().coupons().allAfter(this.valueDate());
    }

    @CheckForNull
    default BondCoupon previousCoupon() {
        return this.bond().coupons().latestBefore(this.valueDate());
    }

    @CheckForNull
    default BondCoupon currentCoupon() {
        return this.bond().coupons().nextOnOrAfter(this.valueDate());
    }

    @Nonnull
    InterestRate discountRate(CurrencyId currencyId);

    default Money<C> discount(final Money<?> money, final LocalDate paymentDate) {
        final Money<?> moneyPv = this.discountRate(money.currencyId()).discount(money, this.valueDate(), paymentDate, this.interestRateInterpolator());
        return this.convert(moneyPv);
    }

    InterestRateInterpolator interestRateInterpolator();

    <F extends CurrencyId> Money<C> convert(@Nonnull Money<F> money);

    InterestRate interestRate(@Nonnull BondCoupon coupon);

}
