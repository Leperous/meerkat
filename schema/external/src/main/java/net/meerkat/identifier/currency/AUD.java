package net.meerkat.identifier.currency;

/**
 *
 * @author Ollie
 */
public class AUD extends NationalCurrencyIso {

    private static final long serialVersionUID = 1L;

    AUD() {
    }

    @Override
    public String symbol() {
        return "$";
    }

    @Override
    public String name() {
        return "Australian Dollar";
    }

}
