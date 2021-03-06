package net.meerkat.identifier.country;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import javax.annotation.CheckReturnValue;

import net.meerkat.StringWrapper;
import net.meerkat.identifier.Iso;

/**
 *
 * @author Ollie
 */
public class CountryIso
        extends StringWrapper
        implements Iso, CountryId {

    private static final Map<String, CountryIso> cache = new ConcurrentHashMap<>(32);
    private static final Pattern PATTERN = Pattern.compile("[A-Z]{2}");

    public static final CountryIso AU = valueOf("AU");
    public static final CountryIso EU = valueOf("EU");
    public static final CountryIso JP = valueOf("JP");
    public static final CountryIso GB = valueOf("GB");
    public static final CountryIso US = valueOf("US");

    public static CountryIso valueOf(final String iso) {
        return cache.computeIfAbsent(iso.toUpperCase(), CountryIso::new);
    }

    CountryIso(final String iso) {
        super(iso);
        if (!PATTERN.matcher(iso).matches()) {
            throw new IllegalArgumentException("Invalid country ISO: " + iso);
        }
    }

    @Override
    public CountryIso countryId() {
        return this;
    }

    @Override
    public char first() {
        return super.first();
    }

    @Override
    public String value() {
        return super.value();
    }

    @Override
    public String standard() {
        return "3166-1";
    }

    @CheckReturnValue
    public CountrySubdivisionIso subdivision(final String subdivision) {
        return new CountrySubdivisionIso(this, subdivision);
    }

    public boolean isUserAssigned() {
        switch (this.value()) {
            case "AA":
            case "QM":
            case "QN":
            case "QO":
            case "QP":
            case "QQ":
            case "QR":
            case "QS":
            case "QT":
            case "QU":
            case "QV":
            case "QW":
            case "QX":
            case "QY":
            case "QZ":
            case "ZZ":
                return true;
            default:
                return this.first() == 'X';
        }
    }

}
