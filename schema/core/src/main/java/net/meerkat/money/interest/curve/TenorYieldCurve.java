package net.meerkat.money.interest.curve;

import java.util.Comparator;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import net.ollie.goat.numeric.interpolation.Interpolator;
import net.ollie.goat.numeric.percentage.Percentage;

/**
 *
 * @author ollie
 */
@XmlRootElement
public class TenorYieldCurve extends AbstractYieldCurve<Tenor, TenorYieldCurve> {

    public TenorYieldCurve(final Map<Tenor, Percentage> curve) {
        super(curve, Comparator.naturalOrder());
    }

    @Override
    protected TenorYieldCurve toCurve(final Map<Tenor, Percentage> curve) {
        return new TenorYieldCurve(curve);
    }

    @Override
    public Map.Entry<Tenor, Percentage> interpolate(final Tenor key, final Interpolator<Tenor, Percentage> interpolator) {
        return super.interpolate(key, interpolator);
    }

}
