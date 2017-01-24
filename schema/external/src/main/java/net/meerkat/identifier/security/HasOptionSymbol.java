package net.meerkat.identifier.security;

import java.util.Optional;

import net.meerkat.identifier.instrument.HasInstrumentIds;

/**
 *
 * @author Ollie
 */
public interface HasOptionSymbol extends HasInstrumentIds {

    default Optional<OptionSymbol> optionSymbol() {
        return this.instrumentId(OptionSymbol.class);
    }

}
