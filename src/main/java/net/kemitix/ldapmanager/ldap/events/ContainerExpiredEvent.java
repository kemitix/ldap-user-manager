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

package net.kemitix.ldapmanager.ldap.events;

import lombok.Getter;
import net.kemitix.ldapmanager.ldap.LdapNameUtil;

import javax.naming.Name;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Raised when the container on the server has been updated and the application needs to reload it.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public final class ContainerExpiredEvent {

    @Getter
    private final Set<Name> containers;

    private ContainerExpiredEvent(final Set<Name> containers) {
        this.containers = containers;
    }

    /**
     * Create new {@link ContainerExpiredEvent} to indicate that the application needs to reload the
     * containers containing the supplied names from the LDAP server.
     *
     * @param dn varargs array of items whos containers are now expired
     *
     * @return the event
     */
    @SuppressWarnings("InstantiationOfUtilityClass")
    public static ContainerExpiredEvent containing(final Name... dn) {
        return new ContainerExpiredEvent(Arrays.stream(dn)
                                               .map(LdapNameUtil::getParent)
                                               .filter(Optional::isPresent)
                                               .map(Optional::get)
                                               .collect(Collectors.toSet()));
    }

    /**
     * Create a new {@link ContainerExpiredEvent} to indicate the the application needs to reload the container
     * specified from the LDAP Server.
     *
     * @param dn the container that is now expired.
     *
     * @return the event
     */
    public static ContainerExpiredEvent of(final Name dn) {
        return new ContainerExpiredEvent(Collections.singleton(dn));
    }
}
