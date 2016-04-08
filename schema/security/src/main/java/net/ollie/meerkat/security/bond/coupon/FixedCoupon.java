package net.ollie.meerkat.security.bond.coupon;

import java.time.LocalDate;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

import net.ollie.meerkat.identifier.currency.CurrencyId;
import net.ollie.meerkat.numeric.Percentage;
import net.ollie.meerkat.numeric.interest.FixedInterestRate;
import net.ollie.meerkat.numeric.interest.daycount.YearCount;
import net.ollie.meerkat.numeric.money.Money;
import net.ollie.meerkat.security.fx.CashPayment;

/**
 *
 * @author Ollie
 */
@XmlRootElement(name = "fixed")
public class FixedCoupon<C extends CurrencyId>
        extends AbstractBondCoupon
        implements CashPayment<C> {

    @XmlElementRef(name = "amount")
    private Money<C> amount;

    @XmlElementRef(name = "day_count")
    private FixedInterestRate rate;

    @Deprecated
    FixedCoupon() {
    }

    public FixedCoupon(final LocalDate paymentDate, final Money<C> amount, final FixedInterestRate rate) {
        super(paymentDate);
        this.amount = amount;
        this.rate = rate;
    }

    @Override
    public boolean hasReferenceRate() {
        return false;
    }

    @Override
    public Money<C> amount() {
        return amount;
    }

    @Nonnull
    public FixedInterestRate interestRate() {
        return rate;
    }

    @Override
    public Percentage spread() {
        return rate.annualRate();
    }

    @Override
    public LocalDate date() {
        return this.paymentDate();
    }

    public Money<?> accrue(final LocalDate to) {
        return rate.accrue(amount, this.paymentDate(), to);
    }

    public YearCount yearCount() {
        return rate.yearCount();
    }

}
