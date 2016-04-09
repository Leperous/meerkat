package net.ollie.meerkat.price.bond;

import com.google.common.collect.Maps;

import java.time.LocalDate;
import java.util.List;
import static java.util.Objects.requireNonNull;
import java.util.SortedMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import net.ollie.meerkat.calculate.fx.ExchangeRateCalculator;
import net.ollie.meerkat.calculate.price.bond.BondPrice;
import net.ollie.meerkat.calculate.price.bond.BondShifts;
import net.ollie.meerkat.calculate.price.bond.BondTypePricer;
import net.ollie.meerkat.identifier.currency.CurrencyId;
import net.ollie.meerkat.numeric.interest.InterestRate;
import net.ollie.meerkat.numeric.money.Money;
import net.ollie.meerkat.numeric.money.fx.ExchangeRate;
import net.ollie.meerkat.security.bond.FixedCouponBond;
import net.ollie.meerkat.security.bond.FixedCouponBond.FixedCouponBondCoupons;
import net.ollie.meerkat.security.bond.coupon.FixedCoupon;
import net.ollie.meerkat.security.fx.CashPayment;

import org.apache.commons.math3.fraction.Fraction;

/**
 * Prices fixed coupon bonds purely based on their coupon rate.
 *
 * @author ollie
 */
public class ZeroSpreadFixedCouponBondPricer implements BondTypePricer<LocalDate, FixedCouponBond> {

    private final Function<? super LocalDate, ? extends ExchangeRateCalculator> getFxRates;
    private final BiFunction<? super LocalDate, ? super CurrencyId, ? extends InterestRate> getDiscountRate;

    public ZeroSpreadFixedCouponBondPricer(
            final Function<LocalDate, ExchangeRateCalculator> exchangeRates,
            final BiFunction<LocalDate, CurrencyId, InterestRate> discountRates) {
        this.getFxRates = exchangeRates;
        this.getDiscountRate = discountRates;
    }

    @Override
    public <C extends CurrencyId> BondPrice<C> price(
            final LocalDate date,
            final FixedCouponBond bond,
            final C currency) {
        return this.price(date, bond.nominal(), bond.coupons(), currency);
    }

    public <P extends CurrencyId, Z extends CurrencyId, C extends CurrencyId> BondPrice<C> price(
            final LocalDate date,
            final CashPayment<P> par,
            final FixedCouponBondCoupons<Z> coupons,
            final C currency) {

        final ExchangeRateCalculator fxRates = requireNonNull(getFxRates.apply(date));
        final ExchangeRate<P, C> parFxRate = fxRates.rate(par.currencyId(), currency);
        final ExchangeRate<Z, C> couponFxRate = fxRates.rate(coupons.currencyId(), currency);

        final InterestRate discountRate = requireNonNull(getDiscountRate.apply(date, currency));

        return new ZeroSpreadFixedCouponBondPrice<>(date, par, coupons, parFxRate, couponFxRate, discountRate, BondShifts.none());

    }

    private static final class ZeroSpreadFixedCouponBondPrice<P extends CurrencyId, Z extends CurrencyId, C extends CurrencyId>
            implements BondPrice<C> {

        private final LocalDate valuationDate;
        private final CashPayment<P> par;
        private final FixedCouponBondCoupons<Z> coupons;
        private final ExchangeRate<P, C> parFxRate;
        private final ExchangeRate<Z, C> couponFxRate;
        private final InterestRate discountRate;
        private final BondShifts shifts;

        ZeroSpreadFixedCouponBondPrice(
                final LocalDate valuationDate,
                final CashPayment<P> par,
                final FixedCouponBondCoupons<Z> coupons,
                final ExchangeRate<P, C> parFxRate,
                final ExchangeRate<Z, C> couponFxRate,
                final InterestRate discountRate,
                final BondShifts shifts) {
            this.valuationDate = valuationDate;
            this.par = par;
            this.coupons = coupons;
            this.shifts = shifts;
            this.parFxRate = parFxRate;
            this.couponFxRate = couponFxRate;
            this.discountRate = discountRate;
        }

        @Override
        public Money<C> parValue() {
            return this.parFxRate().convert(par.amount());
        }

        @Override
        public Money<C> cleanValue() {
            final InterestRate discountRate = this.discountRate();
            final Money<C> presentParValue = this.presentParValue(discountRate);
            return this.cleanFlow(valuationDate, this.maturity(), discountRate)
                    .values()
                    .stream()
                    .reduce(presentParValue, Money::plus);
        }

        @Override
        public List<CashPayment<C>> cleanFlow(final LocalDate startInclusive, final LocalDate endExclusive) {
            final InterestRate discountRate = this.discountRate();
            return this.cleanFlow(startInclusive, endExclusive, discountRate)
                    .entrySet()
                    .stream()
                    .map(entry -> CashPayment.of(entry.getKey(), entry.getValue()))
                    .collect(Collectors.toList());
        }

        @Override
        public Money<C> accruedInterest() {
            final FixedCoupon<Z> prior = coupons.prior(valuationDate);
            final Fraction years = prior.yearCount().yearsBetween(valuationDate, valuationDate);
            final Money<C> couponAmount = this.couponFxRate().convert(prior.amount());
            return couponAmount.times(years);
        }

        @Override
        public BondPrice<C> shift(final BondShifts shifts) {
            return new ZeroSpreadFixedCouponBondPrice<>(valuationDate, par, coupons, parFxRate, couponFxRate, discountRate, shifts);
        }

        ExchangeRate<P, C> parFxRate() {
            return shifts.shift(parFxRate);
        }

        ExchangeRate<Z, C> couponFxRate() {
            return shifts.shift(couponFxRate);
        }

        LocalDate maturity() {
            return par.date();
        }

        InterestRate discountRate() {
            return shifts.shift(discountRate);
        }

        Money<C> presentValue(final Money<C> amount, final LocalDate outDate, final InterestRate discountRate) {
            return discountRate.discount(amount, valuationDate, outDate);
        }

        Money<C> presentParValue(final InterestRate discountRate) {
            return this.presentValue(this.parValue(), this.maturity(), discountRate);
        }

        SortedMap<LocalDate, Money<C>> cleanFlow(LocalDate startInclusive, LocalDate endExclusive, InterestRate discountRate) {
            final ExchangeRate<Z, C> fxRate = this.couponFxRate();
            final List<FixedCoupon<Z>> coupons = this.coupons.between(startInclusive, endExclusive);
            final SortedMap<LocalDate, Money<C>> flow = Maps.newTreeMap();
            for (final FixedCoupon<Z> coupon : coupons) {
                final Money<C> money = fxRate.convert(coupon.amount());
                flow.compute(coupon.date(), (d, c) -> c == null ? money : money.plus(c));
            }
            return flow;
        }

    }

}