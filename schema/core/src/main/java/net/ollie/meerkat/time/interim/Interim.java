package net.ollie.meerkat.time.interim;

import java.time.LocalDate;
import java.util.Optional;

import javax.annotation.Nonnull;

/**
 *
 * @author ollie
 */
public interface Interim {

    boolean contains(LocalDate date);

    @Nonnull
    Optional<Interval> closed();

}