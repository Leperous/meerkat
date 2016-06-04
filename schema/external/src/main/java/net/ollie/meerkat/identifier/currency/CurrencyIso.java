package net.ollie.meerkat.identifier.currency;

import java.io.Serializable;

import net.ollie.goat.money.currency.Currency;
import net.ollie.meerkat.identifier.Iso;
import net.ollie.meerkat.identifier.country.CountryIso;
import net.ollie.meerkat.identifier.country.HasCountryId;
import net.ollie.meerkat.identifier.security.SecurityId;

/**
 * ISO 4217 currency code.
 *
 * @author Ollie
 */
public interface CurrencyIso
        extends Iso, Currency, HasCountryId, SecurityId, Serializable {

    default boolean isReserved() {
        return this.first() == 'X';
    }

    @Override
    @Deprecated
    default CurrencyIso currency() {
        return this;
    }

    @Override
    default CountryIso countryId() {
        return CountryIso.valueOf(this.value().substring(0, 2));
    }

    @Override
    default String standard() {
        return "4217";
    }

    AUD AUD = new AUD();
    CAD CAD = new CAD();
    CHF CHF = new CHF();
    CNY CNY = new CNY();
    EUR EUR = new EUR();
    GBP GBP = new GBP();
    JPY JPY = new JPY();
    INR INR = new INR();
    NZD NZD = new NZD();
    USD USD = new USD();
    ZAR ZAR = new ZAR();

}
