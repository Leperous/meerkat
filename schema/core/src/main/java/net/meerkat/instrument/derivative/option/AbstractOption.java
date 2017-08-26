package net.meerkat.instrument.derivative.option;

import net.meerkat.identifier.instrument.InstrumentIds;
import net.meerkat.instrument.Instrument;
import net.meerkat.instrument.IssuedSecurity;
import net.meerkat.instrument.derivative.option.exercise.OptionExercise;
import net.meerkat.issue.Issue;
import net.meerkat.money.Money;

/**
 *
 * @author ollie
 */
public abstract class AbstractOption<S extends Instrument>
        extends IssuedSecurity
        implements Option<S> {

    private static final long serialVersionUID = 1L;

    private final OptionExercise exercise;
    private final Money<?> premium;
    private final Money<?> strike;
    private final Number contractMultiplier;

    protected AbstractOption(
            final String name,
            final InstrumentIds identifiers,
            final Issue issue,
            final OptionExercise exercise,
            final Money<?> premium,
            final Money<?> strike,
            final Number contractMultiplier) {
        super(name, identifiers, issue);
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
    public Number contractMultiplier() {
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
