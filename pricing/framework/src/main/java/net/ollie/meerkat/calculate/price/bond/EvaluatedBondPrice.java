package net.ollie.meerkat.calculate.price.bond;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import javax.xml.bind.annotation.XmlElementRef;

import net.ollie.goat.currency.Currency;
import net.ollie.goat.money.Money;
import net.ollie.meerkat.calculate.price.EvaluatedSecurityPrice;

/**
 *
 * @author ollie
 */
public class EvaluatedBondPrice<C extends Currency>
        extends EvaluatedSecurityPrice<C>
        implements BondPrice<C> {

    private static final long serialVersionUID = 1L;

    @XmlElementRef(name = "par")
    private Money<C> par;

    @Deprecated
    protected EvaluatedBondPrice() {
    }

    public EvaluatedBondPrice(final Money<C> clean, final Money<C> dirty,
            final Money<C> par) {
        super(clean, dirty);
        this.par = par;
    }

    @Override
    public Money<C> par() {
        return par;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);
        out.writeObject(par);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
        par = (Money<C>) in.readObject();
    }

}
