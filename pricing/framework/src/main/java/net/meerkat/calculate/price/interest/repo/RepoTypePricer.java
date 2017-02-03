package net.meerkat.calculate.price.interest.repo;

import java.time.temporal.Temporal;

import net.meerkat.calculate.price.InstrumentPriceException;
import net.meerkat.calculate.price.shifts.ExchangeRateShifts.ExchangeRateShifter;
import net.meerkat.instrument.interest.repo.Repo;
import net.meerkat.identifier.currency.CurrencyId;
import net.meerkat.calculate.price.InstrumentPricer;

/**
 *
 * @author ollie
 */
public interface RepoTypePricer<T extends Temporal, R extends Repo<?>>
        extends InstrumentPricer<T, R>, ExchangeRateShifter {

    @Override
    <C extends CurrencyId> RepoPrice.Shiftable<C> price(T valuation, R security, C currency)
            throws RepoPriceException;

    class RepoPriceException extends InstrumentPriceException {

        private static final long serialVersionUID = 1L;

        public RepoPriceException(final String message) {
            super(message);
        }

        public RepoPriceException(final String message, final Exception cause) {
            super(message, cause);
        }

    }

}