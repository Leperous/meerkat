package net.ollie.meerkat.security;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import javax.annotation.OverridingMethodsMustInvokeSuper;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.ollie.meerkat.identifier.security.HasSecurityIds;
import net.ollie.meerkat.identifier.security.SecurityIds;
import net.ollie.meerkat.utils.HasName;

/**
 *
 * @author Ollie
 */
@XmlRootElement
public class NamedSecurity
        implements Security, HasSecurityIds, HasName, Externalizable {

    @XmlElement(name = "ids", required = true)
    private SecurityIds identifiers;

    @XmlAttribute(name = "name", required = true)
    private String name;

    @Deprecated
    protected NamedSecurity() {
    }

    public NamedSecurity(final String name, final SecurityIds identifiers) {
        this.name = name;
        this.identifiers = identifiers;
    }

    @Override
    public SecurityIds securityIds() {
        return identifiers;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + ": " + name;
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(identifiers);
        out.writeUTF(name);
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        identifiers = (SecurityIds) in.readObject();
        name = in.readUTF();
    }

}
