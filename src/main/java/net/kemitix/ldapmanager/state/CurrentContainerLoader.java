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

import lombok.val;
import net.kemitix.ldapmanager.domain.OU;
import net.kemitix.ldapmanager.domain.User;
import net.kemitix.ldapmanager.events.CurrentContainerChangedEvent;
import net.kemitix.ldapmanager.ldap.ObjectClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.stereotype.Component;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Loads and updates the contents of the current container from the LDAP server.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Component
class CurrentContainerLoader {

    private final LdapTemplate ldapTemplate;

    private final LdapEntityContainerMap containerMap;

    private final CurrentContainer currentContainer;

    private final LogMessages logMessages;

    /**
     * Constructor.
     *
     * @param ldapTemplate     The LDAP Template.
     * @param containerMap     The Map to LdapEntity containers
     * @param currentContainer The current container
     * @param logMessages      The Log messages
     */
    @Autowired
    CurrentContainerLoader(
            final LdapTemplate ldapTemplate, final LdapEntityContainerMap containerMap,
            final CurrentContainer currentContainer, final LogMessages logMessages
                          ) {
        this.ldapTemplate = ldapTemplate;
        this.containerMap = containerMap;
        this.currentContainer = currentContainer;
        this.logMessages = logMessages;
    }

    /**
     * Load the contents of the container from the LDAP server.
     */
    @EventListener(CurrentContainerChangedEvent.class)
    public void loadContainer() {
        val dn = currentContainer.getDn();
        logMessages.add("Loading container: " + dn.toString());
        val whereObjectClass = LdapQueryBuilder.query()
                                               .base(dn)
                                               .where("objectclass");
        val ouList = ldapTemplate.find(whereObjectClass.is(ObjectClass.ORGANIZATIONAL_UNIT), OU.class);
        val userList = ldapTemplate.find(whereObjectClass.is(ObjectClass.INET_ORG_PERSON), User.class);
        containerMap.put(dn, LdapEntityContainer.of(Stream.of(ouList.stream(), userList.stream())
                                                          .flatMap(Function.identity())
                                                          .collect(Collectors.toList())));
        logMessages.add(String.format("Loaded container: %d OUs and %d users", ouList.size(), userList.size()));
    }
}
