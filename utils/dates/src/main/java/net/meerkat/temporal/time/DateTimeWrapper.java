package net.meerkat.temporal.time;

import net.meerkat.temporal.PartialDateTime;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.util.Optional;

public class DateTimeWrapper implements PartialDateTime {

    private final ZonedDateTime zonedDateTime;

    public DateTimeWrapper(final ZonedDateTime zonedDateTime) {
        this.zonedDateTime = zonedDateTime;
    }

    @Nonnull
    @Override
    public LocalDate date() {
        return zonedDateTime.toLocalDate();
    }

    @Nonnull
    @Override
    public ZoneId zoneId() {
        return zonedDateTime.getZone();
    }

    @Override
    public Optional<LocalTime> time() {
        return Optional.of(zonedDateTime.toLocalTime());
    }

    @Nonnull
    @Override
    @Deprecated
    public Optional<ZonedDateTime> toDateTime() {
        return Optional.of(zonedDateTime);
    }

    @Nonnull
    public ZonedDateTime zonedDateTime() {
        return zonedDateTime;
    }

    @Override
    public long until(final Temporal endExclusive, final TemporalUnit unit) {
        return zonedDateTime.until(endExclusive, unit);
    }

    @Override
    public long getLong(final TemporalField field) {
        return zonedDateTime.getLong(field);
    }

    @Override
    public boolean isSupported(TemporalUnit unit) {
        return zonedDateTime.isSupported(unit);
    }

    @Override
    public Temporal with(final TemporalField field, final long newValue) {
        return zonedDateTime.with(field, newValue);
    }

    @Override
    public DateTimeWrapper plus(long amountToAdd, TemporalUnit unit) {
        return new DateTimeWrapper(zonedDateTime.plus(amountToAdd, unit));
    }

    @Override
    public boolean isSupported(final TemporalField field) {
        return zonedDateTime.isSupported(field);
    }

    @Override
    public String toString() {
        return zonedDateTime.toString();
    }

}
