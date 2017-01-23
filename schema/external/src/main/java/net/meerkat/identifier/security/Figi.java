package net.meerkat.identifier.security;

import net.meerkat.StringWrapper;

/**
 * Fixed Instrument Global Identifier.
 *
 * @author ollie
 * @see
 * <a href="https://en.wikipedia.org/wiki/Financial_Instrument_Global_Identifier">FIGI</a>
 */
public class Figi
        extends StringWrapper
        implements HasCheckDigit, SecurityId {

    private static final long serialVersionUID = 1L;

    @Deprecated
    Figi() {
    }

    public Figi(final String value) {
        super(value);
    }

    @Override
    public char checkDigit() {
        return this.last();
    }

    @Override
    public char computeCheckDigit() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}