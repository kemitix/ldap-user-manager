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

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import net.kemitix.ldapmanager.events.CurrentContainerChangedEvent;
import net.kemitix.ldapmanager.ldap.LdapNameUtil;
import net.kemitix.ldapmanager.navigation.events.NavigationItemOuActionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.naming.Name;

/**
 * Contains the DN of the current container relative to the base DN.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Component
class DefaultCurrentContainer implements CurrentContainer {

    private final ApplicationEventPublisher eventPublisher;

    @Setter
    @Getter
    private Name dn;

    /**
     * Constructor.
     *
     * @param eventPublisher The Application Event Publisher
     */
    @Autowired
    DefaultCurrentContainer(final ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    /**
     * Initializer.
     */
    @PostConstruct
    public void init() {
        dn = LdapNameUtil.empty();
        publishCurrentContainer();
    }

    /**
     * Updates the DN when a navigation item for an OU is selected, then publishes that the OU has changed.
     *
     * @param event the event containing the {@link net.kemitix.ldapmanager.domain.LdapEntity} for the item selected.
     */
    @EventListener(NavigationItemOuActionEvent.class)
    public void onNavigationItemOuAction(final NavigationItemOuActionEvent event) {
        val newDn = event.getOu()
                         .getDn();
        if (!newDn.equals(dn)) {
            dn = newDn;
            publishCurrentContainer();
        }
    }

    @Override
    public void publishCurrentContainer() {
        eventPublisher.publishEvent(CurrentContainerChangedEvent.of(dn));
    }
}
