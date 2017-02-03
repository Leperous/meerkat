package net.meerkat.calculate.price;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import javax.annotation.OverridingMethodsMustInvokeSuper;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

import net.meerkat.money.Money;
import net.meerkat.identifier.currency.CurrencyId;

/**
 *
 * @author ollie
 */
@XmlRootElement
public class EvaluatedInstrumentPrice<C extends CurrencyId>
        implements InstrumentPrice<C>, Externalizable {

    private static final long serialVersionUID = 1L;

    @XmlElementRef(name = "clean")
    private Money<C> clean;

    @XmlElementRef(name = "dirty")
    private Money<C> dirty;

    @Deprecated
    protected EvaluatedInstrumentPrice() {
    }

    public EvaluatedInstrumentPrice(final Money<C> clean, final Money<C> dirty) {
        this.clean = clean;
        this.dirty = dirty;
    }

    @Override
    public Money<C> clean() {
        return clean;
    }

    @Override
    public Money<C> dirty() {
        return dirty;
    }

    @Override
    @Deprecated
    public EvaluatedInstrumentPrice<C> evaluate() {
        return this;
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(clean);
        out.writeObject(dirty);
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    @SuppressWarnings("unchecked")
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        clean = (Money<C>) in.readObject();
        dirty = (Money<C>) in.readObject();
    }

}