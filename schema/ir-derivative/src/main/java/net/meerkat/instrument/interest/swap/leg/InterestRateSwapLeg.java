package net.meerkat.instrument.interest.swap.leg;

import java.util.Map;

import javax.annotation.Nonnull;

import net.meerkat.Explainable;
import net.meerkat.identifier.currency.CurrencyId;
import net.meerkat.identifier.currency.HasCurrencyIds;
import net.meerkat.instrument.derivative.swap.SwapLeg;
import net.meerkat.money.interest.InterestRate;
import net.meerkat.money.interest.InterestRateOrId;
import net.meerkat.money.interest.InterestRateProvider;

/**
 *
 * @author ollie
 */
public interface InterestRateSwapLeg<P extends CurrencyId, R extends CurrencyId>
        extends SwapLeg<P, R>, HasCurrencyIds, Explainable {

    @Nonnull
    InterestRateOrId payRate();

    @Nonnull
    InterestRateOrId receiveRate();

    @Nonnull
    default InterestRate payRate(final InterestRateProvider interestRateProvider) {
        return this.payRate().resolve(interestRateProvider);
    }

    @Nonnull
    default InterestRate receiveRate(final InterestRateProvider interestRateProvider) {
        return this.receiveRate().resolve(interestRateProvider);
    }

    @Override
    default Map<String, Object> explain() {
        return this.explanationBuilder()
                .put("pay rate", this.payRate())
                .put("pay currency", this.payCurrency())
                .put("receive rate", this.receiveRate())
                .put("receive currency", this.receiveCurrency());
    }

}