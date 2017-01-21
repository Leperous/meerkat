package net.ollie.meerkat.identifier.currency;

/**
 *
 * @author ollie
 */
public class CNH extends AbstractCurrencyIso {

    private static final long serialVersionUID = 1L;

    CNH() {
    }

    @Override
    public String symbol() {
        return "¥";
    }

    @Override
    public String name() {
        return "Chinese Offshore Yuan";
    }

}
