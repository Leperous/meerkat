package net.meerkat.instrument.derivative.option;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;

import net.meerkat.identifier.instrument.InstrumentIds;
import net.meerkat.instrument.IssuedSecurity;
import net.meerkat.instrument.derivative.option.exercise.OptionExercise;
import net.meerkat.issuer.IssuerId;
import net.meerkat.money.Money;
import net.meerkat.numeric.quantity.Quantity;
import net.meerkat.security.Instrument;

/**
 *
 * @author ollie
 */
public abstract class AbstractOption<S extends Instrument>
        extends IssuedSecurity
        implements Option<S> {

    private static final long serialVersionUID = 1L;

    @XmlElementRef(name = "exercise")
    private OptionExercise exercise;

    @XmlElement(name = "premium")
    private Money<?> premium;

    @XmlElement(name = "strike")
    private Money<?> strike;

    @XmlElementRef(name = "multiplier")
    private Quantity contractMultiplier;

    @Deprecated
    protected AbstractOption() {
    }

    public AbstractOption(
            final String name,
            final InstrumentIds identifiers,
            final IssuerId issuer,
            final OptionExercise exercise,
            final Money<?> premium,
            final Money<?> strike,
            final Quantity contractMultiplier) {
        super(name, identifiers, issuer);
        this.exercise = exercise;
        this.premium = premium;
        this.strike = strike;
        this.contractMultiplier = contractMultiplier;
    }

    @Override
    public OptionExercise exercise() {
        return exercise;
    }

    @Override
    public Money<?> strike() {
        return strike;
    }

    @Override
    public Money<?> premium() {
        return premium;
    }

    @Override
    public Quantity contractMultiplier() {
        return contractMultiplier;
    }

    @Override
    public ExplanationBuilder explain() {
        return super.explain()
                .put("exercise", exercise)
                .put("premium", premium)
                .put("strike", strike)
                .put("multiplier", contractMultiplier);
    }

}
