package net.ollie.meerkat.calculate.price;

import javax.annotation.Nonnull;

import net.meerkat.Explainable;
import net.meerkat.money.Money;
import net.meerkat.money.currency.CurrencyId;
import net.meerkat.money.currency.HasCurrency;

/**
 *
 * @author ollie
 */
public interface SecurityPrice<C extends CurrencyId> extends HasCurrency, Explainable {

    @Nonnull
    Money<C> clean();

    @Nonnull
    Money<C> dirty();

    @Override
    default C currency() {
        return this.clean().currency();
    }

    @Nonnull
    default EvaluatedSecurityPrice<C> evaluate() {
        return new EvaluatedSecurityPrice<>(this.clean(), this.dirty());
    }

    @Override
    default ExplanationBuilder explain() {
        return this.explanationBuilder()
                .put("clean", this.clean())
                .put("dirty", this.dirty());
    }

}
