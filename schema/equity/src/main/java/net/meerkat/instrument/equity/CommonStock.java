package net.meerkat.instrument.equity;

import net.meerkat.identifier.instrument.InstrumentIds;
import net.meerkat.instrument.IssuedSecurity;
import net.meerkat.issue.Issue;

/**
 *
 * @author Ollie
 */
public class CommonStock
        extends IssuedSecurity
        implements Stock {

    public CommonStock(final String name, final InstrumentIds identifiers, final Issue issue) {
        super(name, identifiers, issue);
    }

    @Override
    public <R> R handleWith(final Equity.Handler<R> handler) {
        return handler.handle(this);
    }

}
