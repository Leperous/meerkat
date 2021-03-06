package net.meerkat.money;

import net.meerkat.identifier.currency.CurrencyId;
import net.meerkat.numeric.decimal.BigDecimalFraction;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * @author Ollie
 */
public class FractionalMoney<C extends CurrencyId>
        implements Money<C>, Externalizable {

    private static final long serialVersionUID = 1L;

    @XmlElementRef(name = "currency")
    private C currency;

    @XmlElement(name = "fraction")
    private BigDecimalFraction fraction;

    FractionalMoney() {
    }

    public FractionalMoney(final C currency, final BigDecimalFraction fraction) {
        this.currency = currency;
        this.fraction = fraction;
    }

    public FractionalMoney<C> with(final BigDecimalFraction fraction) {
        return new FractionalMoney<>(currency, fraction);
    }

    @Override
    public C currencyId() {
        return currency;
    }

    @Override
    public BigDecimalFraction value() {
        return fraction;
    }

    @Override
    public FractionalMoney<C> plus(final Money<C> that) {
        throw new UnsupportedOperationException(); //TODO
    }

    @Override
    public FractionalMoney<C> times(final Number n) {
        return this.with(fraction.times(n));
    }

    @Override
    @Deprecated
    public FractionalMoney<C> times(final Number that, final RoundingMode rounding) {
        return this.times(that);
    }

    @Override
    public BigDecimal decimalValue(final MathContext context) {
        return fraction.decimalValue(context);
    }

    @Override
    public String toString() {
        return this.toString(MoneyFormat.UNIQUE_SYMBOL_AMOUNT);
    }

    @Override
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeObject(currency);
        out.writeObject(fraction);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        currency = (C) in.readObject();
        fraction = (BigDecimalFraction) in.readObject();
    }

}
