package net.meerkat.instrument;

import javax.annotation.Nonnull;

import net.meerkat.instrument.cash.CashPayment;
import net.meerkat.instrument.moneymarket.CertificateOfDeposit;
import net.meerkat.instrument.repo.Repo;
import net.meerkat.money.interest.fixed.FixedInterestRate;

/**
 *
 * @author ollie
 */
public interface FixedInterestSecurity extends InstrumentDefinition {

    @Nonnull
    CashPayment<?> purchasePrice();

    @Nonnull
    FixedInterestRate impliedRate();

    <R> R handleWith(Handler<R> handler);

    interface Handler<R> extends InstrumentDefinition.Handler<R> {

        R handle(Repo repo);

        R handle(CertificateOfDeposit certificateOfDeposit);

    }

}
