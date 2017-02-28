/*
The MIT License (MIT)

Copyright (c) 2016 Paul Campbell

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package net.kemitix.ldapmanager.domain;

import org.immutables.value.Value;

import javax.naming.Name;
import java.util.EnumSet;
import java.util.Set;

/**
 * An Organizational Unit.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Value.Immutable(builder = false)
public interface OU extends LdapEntity {

    @Override
    @Value.Parameter
    Name getDn();

    @Override
    @Value.Parameter
    Set<Features> getFeatureSet();

    /**
     * Get the OU name for the container.
     *
     * @return The OU attribute.
     */
    @Value.Parameter
    String getOu();

    /**
     * Create a new OU container that may be renamed.
     *
     * @param dn The DN of the container.
     * @param ou The OU of the container.
     *
     * @return The new OU container.
     */
    static OU of(final Name dn, final String ou) {
        return ImmutableOU.of(dn, EnumSet.of(Features.RENAME), ou);
    }

    /**
     * Create a new OU container that may not be renamed.
     *
     * @param dn The DN of the container.
     * @param ou The OU of the container.
     *
     * @return The new OU container.
     */
    static OU nonRenameable(final Name dn, final String ou) {
        return ImmutableOU.of(dn, EnumSet.noneOf(Features.class), ou);
    }

    /**
     * Return the name (OU attribute) of the OU.
     *
     * @return The name.
     */
    default String name() {
        return getOu();
    }
}
