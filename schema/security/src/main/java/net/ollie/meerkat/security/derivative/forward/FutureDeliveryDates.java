package net.ollie.meerkat.security.derivative.forward;

import java.time.LocalDate;
import java.util.SortedSet;

import javax.annotation.Nonnull;

/**
 *
 * @author ollie
 */
public interface FutureDeliveryDates {

    @Nonnull
    SortedSet<LocalDate> dates();

    default LocalDate earliest() {
        return this.dates().first();
    }

    default LocalDate latest() {
        return this.dates().last();
    }

}
