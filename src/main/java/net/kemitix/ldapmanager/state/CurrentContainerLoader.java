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

package net.kemitix.ldapmanager.state;

import net.kemitix.ldapmanager.events.CurrentContainerChangedEvent;
import net.kemitix.ldapmanager.ldap.LdapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.naming.Name;

/**
 * Loads and updates the contents of the current container from the LDAP server.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Component
class CurrentContainerLoader {

    private final LdapService ldapService;

    private final LdapEntityContainerMap ldapEntityContainerMap;

    private final CurrentContainer currentContainer;

    private final LogMessages logMessages;

    /**
     * Constructor.
     *
     * @param ldapService            The LDAP Service
     * @param ldapEntityContainerMap The Map to LdapEntity containers
     * @param currentContainer       The current container
     * @param logMessages            The Log messages
     */
    @Autowired
    CurrentContainerLoader(
            final LdapService ldapService, final LdapEntityContainerMap ldapEntityContainerMap,
            final CurrentContainer currentContainer, final LogMessages logMessages
                          ) {
        this.ldapService = ldapService;
        this.ldapEntityContainerMap = ldapEntityContainerMap;
        this.currentContainer = currentContainer;
        this.logMessages = logMessages;
    }

    /**
     * Load the contents of the container from the LDAP server.
     */
    @EventListener(CurrentContainerChangedEvent.class)
    public void onCurrentContainerChangedEventLoadLdapContainer() {
        logMessages.add("Updating current container...");
        final Name dn = currentContainer.getDn();
        ldapEntityContainerMap.put(dn, ldapEntityContainerMap.get(dn)
                                                             .orElseGet(() -> ldapService.getLdapEntityContainer(dn)));
        logMessages.add("Current container updated.");
    }
}
