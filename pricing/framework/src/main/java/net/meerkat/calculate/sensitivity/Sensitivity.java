package net.meerkat.calculate.sensitivity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.coljate.util.SelfTyped;
import net.meerkat.utils.Classes.Castable;

/**
 *
 * @author Ollie
 */
public interface Sensitivity extends HasSensitivityId, Castable {

    interface Summable<S extends Summable<S>> extends Sensitivity, SelfTyped<S> {

        @Nonnull
        S plus(@Nullable S that);

        @Nonnull
        S times(Number n);

    }

}
