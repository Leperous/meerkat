package net.meerkat.pricing.shifts.fx;

import java.util.Map;

import net.meerkat.identifier.currency.CurrencyId;
import net.meerkat.money.fx.ExchangeRate;
import net.meerkat.money.fx.ExchangeRateSnapshot;

/**
 *
 * @author Ollie
 */
public interface NoExchangeRateShifts extends ExchangeRateShifts {

    NoExchangeRateShifts INSTANCE = new NoExchangeRateShifts() {

        @Override
        public Map<String, Object> explain() {
            return this.explanationBuilder();
        }

    };

    @Override
    default ExchangeRateSnapshot shift(final ExchangeRateSnapshot rates) {
        return rates;
    }

    @Override
    default <F extends CurrencyId, T extends CurrencyId> ExchangeRate<F, T> shift(final ExchangeRate<F, T> rate) {
        return rate;
    }

}
