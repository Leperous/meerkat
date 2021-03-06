package net.meerkat.identifier.rating.sandp;

import net.meerkat.collections.Sets;
import net.meerkat.risk.rating.CreditRatingBand;

import java.util.Set;

/**
 *
 * @author ollie
 */
public enum SAndPShortTermRating implements SAndPRating {

    A1_PLUS("Extremely strong capacity to meet financial commitments", CreditRatingBand.AAA),
    A1("Strong capacity to meet financial commitments"),
    A2("Satisfactory capacity to meet financial commitments"),
    A3("Adequate capacity to meet financial commitments"),
    B("Uncertain capacity to meet financial commitments"),
    C("Vulnerable to nonpayment"),
    R("Under regulatory supervision"),
    SD("Selective default"),
    D("Default", CreditRatingBand.D),
    NR("Not rated", CreditRatingBand.U);

    private final String description;
    private final Set<CreditRatingBand> bands;

    private SAndPShortTermRating(final String description, final CreditRatingBand... bands) {
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
    public String agency() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
