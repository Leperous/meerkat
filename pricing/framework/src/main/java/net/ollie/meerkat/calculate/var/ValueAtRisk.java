package net.ollie.meerkat.calculate.var;

import javax.annotation.Nonnull;

import net.ollie.goat.money.Money;
import net.ollie.goat.money.currency.Currency;
import net.ollie.goat.money.currency.HasCurrency;
import net.ollie.meerkat.calculate.fx.ExchangeRates;

/**
 * Value at risk (VaR).
 *
 * @author Ollie
 */
public interface ValueAtRisk extends HasCurrency {

    @Nonnull
    Money<?> atRisk();

    @Nonnull
    default <R extends Currency> Money<R> atRisk(final ExchangeRates rates, final R currency) {
        return rates.convert(this.atRisk(), currency);
    }

    @Override
    default Currency currency() {
        return this.atRisk().currency();
    }

}