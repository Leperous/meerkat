package net.ollie.meerkat.identifier.security;

/**
 *
 * @author Ollie
 */
public interface SecurityId extends HasSecurityId {

    @Override
    @Deprecated
    default SecurityId securityId() {
        return this;
    }

}
