package net.meerkat.money.interest.fixed;

import java.time.LocalDate;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

import net.meerkat.identifier.currency.CurrencyId;
import net.meerkat.money.Money;
import net.meerkat.money.interest.InterestRate;
import net.meerkat.money.interest.InterestRateId;
import net.ollie.goat.numeric.percentage.Percentage;
import net.ollie.goat.temporal.date.count.DateArithmetic;
import net.ollie.goat.temporal.date.years.Years;

/**
 *
 * @author ollie
 */
@XmlRootElement
public abstract class FixedInterestRate implements InterestRate, Comparable<FixedInterestRate> {

    @XmlElementRef(name = "id")
    private InterestRateId id;

    @XmlAttribute(name = "annual_rate")
    private Percentage annualRate;

    @XmlElementRef(name = "year_count")
    private DateArithmetic dates;

    @Deprecated
    protected FixedInterestRate() {
    }

    protected FixedInterestRate(
            final InterestRateId id,
            final Percentage annualRate,
            final DateArithmetic dates) {
        this.id = id;
        this.annualRate = annualRate;
        this.dates = dates;
    }

    @Nonnull
    public Percentage annualRate() {
        return annualRate;
    }

    @Override
    public DateArithmetic dateArithmetic() {
        return dates;
    }

    @Override
    public InterestRateId interestRateId() {
        return id;
    }

    public boolean isNegative() {
        return this.annualRate().isNegative();
    }

    @Override
    @Deprecated
    public Percentage spot(final LocalDate date) {
        return this.annualRate();
    }

    @Override
    @Deprecated
    public Percentage forward(final LocalDate fixing, final LocalDate out) {
        return this.annualRate();
    }

    @Override
    public FixedInterestRate plus(final Percentage bump) {
        return this.with(this.annualRate().plus(bump));
    }

    @Override
    public int compareTo(final FixedInterestRate that) {
        return this.annualRate().compareTo(that.annualRate());
    }

    public abstract FixedInterestRate with(Percentage rate);

    @Override
    public <C extends CurrencyId> Money<C> accrue(final Money<C> money, final LocalDate since, final LocalDate until) {
        return this.accrue(money, this.dateArithmetic().yearsBetween(since, until));
    }

    public abstract <C extends CurrencyId> Money<C> accrue(Money<C> money, Years yearsBetween);

}
