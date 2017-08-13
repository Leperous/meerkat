package net.meerkat.money.price;

import net.meerkat.identifier.currency.CurrencyId;
import net.meerkat.money.Money;
import net.ollie.goat.numeric.fraction.DecimalFraction;

/**
 *
 * @author ollie
 */
public class DecimalTwoWayPrice<C extends CurrencyId> implements TwoWayPrice<C> {

    private final C currency;
    private final DecimalFraction bid;
    private final DecimalFraction offer;
    private transient Money<C> bidMoney;
    private transient Money<C> offerMoney;

    public DecimalTwoWayPrice(final C currency, final DecimalFraction bid, final DecimalFraction offer) {
        this.currency = currency;
        this.bid = bid;
        this.offer = offer;
    }

    @Override
    public Money<C> bid() {
        return bidMoney == null
                ? bidMoney = Money.of(currency, bid)
                : bidMoney;
    }

    @Override
    public Money<C> offer() {
        return offerMoney == null
                ? offerMoney = Money.of(currency, offer)
                : offerMoney;
    }

    @Override
    public Money<C> mid() {
        return Money.of(currency, bid.plus(offer).over(2));
    }

}
