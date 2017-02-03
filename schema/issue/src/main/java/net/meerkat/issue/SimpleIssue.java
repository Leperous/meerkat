package net.meerkat.issue;

import javax.xml.bind.annotation.XmlElementRef;

import net.meerkat.Explainable;
import net.meerkat.issuer.IssuerId;

/**
 *
 * @author ollie
 */
public class SimpleIssue implements Issue, Explainable {

    @XmlElementRef(name = "issuerId")
    private IssuerId issuer;

    @Deprecated
    protected SimpleIssue() {
    }

    public SimpleIssue(final IssuerId issuer) {
        this.issuer = issuer;
    }

    @Override
    public IssuerId issuerId() {
        return issuer;
    }

    @Override
    public ExplanationBuilder explain() {
        return this.explanationBuilder()
                .put("issuer", issuer);
    }

}