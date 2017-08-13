package net.meerkat.instrument.fx.forward;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlRootElement;

import net.meerkat.identifier.currency.CurrencyId;
import net.meerkat.instrument.fx.FxInstrument;
import net.meerkat.money.Money;
import net.meerkat.money.fx.ExchangeRate;

/**
 *
 * @author Ollie
 */
@XmlRootElement
public interface FxForward<B extends CurrencyId, C extends CurrencyId>
        extends FxInstrument<B, C> {

    @Nonnull
    Money<B> baseAmount();

    @Override
    default B base() {
        return this.baseAmount().currencyId();
    }

    @Nonnull
    Money<C> counterAmount();

    @Override
    default C counter() {
        return this.counterAmount().currencyId();
    }

    @Override
    default ExchangeRate<B, C> exchangeRate() {
        return ExchangeRate.between(this.baseAmount(), this.counterAmount());
    }

    @Override
    default <R> R handleWith(final FxInstrument.Handler<R> handler) {
        return handler.handle(this);
    }

}
