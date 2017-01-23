package net.meerkat.identifier.rating.bloomberg;

import javax.xml.bind.annotation.XmlRootElement;

import net.meerkat.rating.CreditRatings;

/**
 *
 * @author Ollie
 */
@XmlRootElement
public class BloombergCompositeRating extends CreditRatings {

    private static final long serialVersionUID = 1L;

    @Override
    public String agency() {
        return "Bloomberg";
    }

}