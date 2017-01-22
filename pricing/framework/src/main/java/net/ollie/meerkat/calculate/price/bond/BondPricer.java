package net.ollie.meerkat.calculate.price.bond;

import java.time.temporal.Temporal;

import net.meerkat.money.currency.Currency;
import net.ollie.meerkat.calculate.price.SecurityTypePriceCalculator;
import net.meerkat.security.bond.Bond;
import net.ollie.meerkat.calculate.price.SecurityPriceException;

/**
 * Prices particular types of bond.
 *
 * @author ollie
 * @see GenericBondPricer
 */
public interface BondPricer<T extends Temporal, B extends Bond>
        extends SecurityTypePriceCalculator<T, B> {

    @Override
    default <C extends Currency> BondPrice.Shiftable<C> price(T temporal, B bond, C currency) throws BondPriceException {
        return bond.handleWith(this.priceContext(temporal, currency));
    }

    default <C extends Currency> BondPrice.Shiftable<C> price(final T temporal, final B bond, final C currency, final BondShifts shifts) throws BondPriceException {
        return this.price(temporal, bond, currency).shift(shifts);
    }

    <C extends Currency> GenericBondPricer.BondPriceContext<C> priceContext(T valuation, C currency)
            throws BondPriceException;

    interface BondPriceContext<C extends Currency> extends Bond.Handler<BondPrice.Shiftable<C>> {

    }

    class BondPriceException extends SecurityPriceException {

        private static final long serialVersionUID = 1L;

        public BondPriceException(final String message) {
            super(message);
        }

    }

}
