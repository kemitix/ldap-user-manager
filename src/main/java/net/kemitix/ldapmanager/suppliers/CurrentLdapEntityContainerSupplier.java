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

package net.kemitix.ldapmanager.suppliers;

import net.kemitix.ldapmanager.ldap.LdapService;
import net.kemitix.ldapmanager.state.CurrentContainer;
import net.kemitix.ldapmanager.state.LdapEntityContainer;
import net.kemitix.ldapmanager.state.LdapEntityContainerMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

/**
 * Supplier for the {@link LdapEntityContainer} for the current container.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Component
class CurrentLdapEntityContainerSupplier implements Supplier<LdapEntityContainer> {

    private final CurrentContainer currentContainer;

    private final LdapEntityContainerMap containerMap;

    private final LdapService ldapService;

    /**
     * Constructor.
     *
     * @param currentContainer The current container.
     * @param containerMap     The lookup map of LdapEntity containers.
     * @param ldapService      The LDAP Service.
     */
    @Autowired
    CurrentLdapEntityContainerSupplier(
            final CurrentContainer currentContainer, final LdapEntityContainerMap containerMap,
            final LdapService ldapService
                                      ) {
        this.currentContainer = currentContainer;
        this.containerMap = containerMap;
        this.ldapService = ldapService;
    }

    @Override
    public LdapEntityContainer get() {
        return containerMap.get(currentContainer.getDn());
    }
}
