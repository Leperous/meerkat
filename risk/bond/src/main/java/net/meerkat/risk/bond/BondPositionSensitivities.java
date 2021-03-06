package net.meerkat.risk.bond;

import java.util.Map;
import java.util.Objects;

import javax.annotation.Nonnull;

import net.meerkat.calculate.sensitivity.yield.DollarDuration;
import net.meerkat.pricing.bond.EvaluatedBondPrice;
import net.meerkat.risk.sensitivities.PositionSensitivities;
import net.meerkat.sensitivity.bond.BondUnitPriceSensitivities;
import net.meerkat.sensitivity.bond.BondPriceSensitivities;

/**
 *
 * @author Ollie
 * @see BondPosition
 */
public class BondPositionSensitivities implements BondPriceSensitivities, PositionSensitivities {

    private final BondUnitPriceSensitivities sensitivities;
    private final BondPosition position;

    public BondPositionSensitivities(@Nonnull final BondUnitPriceSensitivities unit, final BondPosition position) {
        this.sensitivities = Objects.requireNonNull(unit, "sensitivities");
        this.position = position;
    }

    @Override
    public EvaluatedBondPrice<?> price() {
        return sensitivities.price();
    }

    @Override
    public DollarDuration dollarDuration() {
        return sensitivities.dollarDuration().times(position.quantity());
    }

    @Override
    public BondPosition position() {
        return position;
    }

    @Override
    public BondUnitPriceSensitivities instrumentSensitivities() {
        return sensitivities;
    }

    @Override
    public Map<String, Object> explain() {
        return this.explanationBuilder()
                .put("sensitivities", sensitivities)
                .put("position", position);
    }

}
