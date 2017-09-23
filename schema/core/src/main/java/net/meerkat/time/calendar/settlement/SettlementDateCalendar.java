package net.meerkat.time.calendar.settlement;

import net.meerkat.time.calendar.Calendar;
import net.meerkat.time.calendar.business.BusinessDayCalendar;

/**
 *
 * @author ollie
 */
public interface SettlementDateCalendar extends Calendar<SettlementDate> {

    static SettlementDateCalendar nthBusinessDay(final int n, final BusinessDayCalendar businessDayCalendar, final SettlementDateCache cache) {
        return new ForwardBusinessDaysSettlementDateCalendar(n, businessDayCalendar, cache);
    }

    static SettlementDateCalendar bothOf(final SettlementDateCalendar first, final SettlementDateCalendar second) {
        return first.equals(second)
                ? first
                : new SettlementDatePairCalendar(first, second);
    }

}
