package net.meerkat.instrument;

import net.meerkat.Explainable;
import net.meerkat.identifier.instrument.HasInstrumentIds;
import net.meerkat.objects.HasName;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.Map;

/**
 *
 * @author Ollie
 */
public interface InstrumentDefinition
        extends Instrument, HasInstrumentIds, HasName, Explainable {

    default String toShortString() {
        return this.instrumentIds().toString();
    }

    @Override
    default Map<String, Object> explain() {
        return this.explanationBuilder();
    }

    @CheckForNull
    <R> R handleWith(@Nonnull InstrumentDefinition.Handler<R> handler);

    interface Handler<R> {

        default R handleUnknown(final InstrumentDefinition instrument) {
            return null;
        }

    }

}
