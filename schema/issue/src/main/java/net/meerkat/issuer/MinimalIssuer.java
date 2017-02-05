package net.meerkat.issuer;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

import net.meerkat.Explainable;
import net.meerkat.rating.CreditRating;

/**
 *
 * @author ollie
 * @see CreditRating
 */
@XmlRootElement
public class MinimalIssuer implements Issuer, Explainable, Externalizable {

    private static final long serialVersionUID = 1L;

    @XmlElementRef(name = "id")
    private IssuerId id;

    @Deprecated
    protected MinimalIssuer() {
    }

    public MinimalIssuer(final IssuerId id) {
        this.id = id;
    }

    @Override
    public IssuerId issuerId() {
        return id;
    }

    @Override
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeObject(id);
    }

    @Override
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        this.id = (IssuerId) in.readObject();
    }

    @Override
    public ExplanationBuilder explain() {
        return this.explanationBuilder()
                .put("id", id);
    }

}
