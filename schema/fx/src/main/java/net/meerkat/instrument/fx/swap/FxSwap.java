package net.meerkat.instrument.fx.swap;

import java.util.Objects;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.meerkat.identifier.currency.CurrencyId;
import net.meerkat.identifier.instrument.InstrumentIds;
import net.meerkat.instrument.derivative.swap.AbstractSwap;
import net.meerkat.instrument.fx.FxDerivative;
import net.meerkat.issue.Issue;
import net.meerkat.utils.collections.sequence.FiniteSequence;

/**
 *
 * @author ollie
 */
@XmlRootElement
public class FxSwap<P extends CurrencyId, R extends CurrencyId>
        extends AbstractSwap
        implements FxDerivative {

    private static final long serialVersionUID = 1L;

    @XmlElement(name = "near", required = true)
    private FxSwapLeg<P, R> near;

    @XmlElement(name = "far", required = true)
    private FxSwapLeg<R, P> far;

    @Deprecated
    FxSwap() {
    }

    public FxSwap(
            final String name,
            final InstrumentIds identifiers,
            final Issue issue,
            final FxSwapLeg<P, R> near,
            final FxSwapLeg<R, P> far) {
        super(name, identifiers, issue);
        this.near = Objects.requireNonNull(near);
        this.far = Objects.requireNonNull(far);
    }

    @Nonnull
    public FxSwapLeg<P, R> nearLeg() {
        return near;
    }

    @Nonnull
    public FxSwapLeg<R, P> farLeg() {
        return far;
    }

    @Override
    public FiniteSequence<FxSwapLeg<?, ?>> legs() {
        return FiniteSequence.of(near, far);
    }

    @Override
    public <R> R handleWith(final FxDerivative.Handler<R> handler) {
        return handler.handle(this);
    }

    @Override
    public ExplanationBuilder explain() {
        return super.explain()
                .put("nearLeg", near)
                .put("farLeg", far);
    }

}