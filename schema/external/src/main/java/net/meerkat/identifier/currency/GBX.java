package net.meerkat.identifier.currency;

/**
 *
 * @author ollie
 */
public class GBX extends NationalCurrencyIso {

    private static final long serialVersionUID = 1L;

    GBX() {
    }

    @Override
    public String symbol() {
        return "p";
    }

    @Override
    public String uniqueSymbol() {
        return "GBp";
    }

    @Override
    public String name() {
        return "Penny Sterling";
    }

}
