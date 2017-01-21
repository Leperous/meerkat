package net.ollie.meerkat.identifier.currency;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ollie
 */
@XmlRootElement
public class GBP extends AbstractCurrencyIso {

    private static final long serialVersionUID = 1L;

    GBP() {
    }

    @Override
    public String symbol() {
        return "£";
    }

    @Override
    public String name() {
        return "Pound Sterling";
    }

}
