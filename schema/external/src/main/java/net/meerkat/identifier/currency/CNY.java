package net.meerkat.identifier.currency;

/**
 *
 * @author Ollie
 */
public class CNY extends NationalCurrencyIso {

    private static final long serialVersionUID = 1L;

    CNY() {
    }

    @Override
    public String symbol() {
        return "¥";
    }

    @Override
    public String name() {
        return "Renminbi";
    }

}
