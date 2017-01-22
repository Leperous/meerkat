package net.ollie.meerkat.numeric.interest;

import javax.annotation.Nonnull;

import net.ollie.goat.money.interest.InterestRate;
import net.ollie.meerkat.security.Security;

/**
 *
 * @author Ollie
 */
public interface InterestRateSecurity extends Security {

    @Nonnull
    InterestRate interestRate();

}
