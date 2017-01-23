package net.meerkat.identifier.rating.moodys;

import java.util.Set;

import net.meerkat.rating.CreditRating;
import net.meerkat.rating.CreditRatingBand;
import static net.meerkat.rating.CreditRatingBand.*;
import net.ollie.goat.collection.Sets;

/**
 *
 * @author ollie
 */
public enum MoodysNationalShortTermRating implements MoodysRating {

    N1("Strong ability to repay short-term debt", AAA, AA, A),
    N2("Above average ability to repay short-term debt", BBB),
    N3("Average ability to repay short-term debt", BB),
    N4("Below average ability to repay short-term debt", B),
    NR("Not rated", U);

    private final String description;
    private final Set<CreditRatingBand> bands;

    private MoodysNationalShortTermRating(final String description, final CreditRatingBand... bands) {
        this.description = description;
        this.bands = Sets.asUnmodifiableSet(bands);
    }

    @Override
    public String description() {
        return description;
    }

    @Override
    public Set<CreditRatingBand> bands() {
        return bands;
    }
    
    @Override
    public int compareTo(final CreditRating that) {
        return that instanceof MoodysNationalShortTermRating
                ? this.compareTo((MoodysNationalShortTermRating) that)
                : MoodysRating.super.compareTo(that);
    }

}