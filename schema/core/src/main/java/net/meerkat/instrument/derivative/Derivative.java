package net.meerkat.instrument.derivative;

import javax.annotation.Nonnull;

import net.meerkat.instrument.Instrument;
import net.meerkat.instrument.Security;

/**
 *
 * @author ollie
 */
public interface Derivative<I extends Instrument> extends Security {

    @Nonnull
    I underlying();

    @Nonnull
    default Instrument ultimateUnderlying() {
        Instrument self = this;
        while (true) {
            Instrument underlying = self instanceof Derivative
                    ? ((Derivative) self).underlying()
                    : null;
            if (underlying == null || underlying == self) {
                return self;
            }
            self = underlying;
        }
    }

}