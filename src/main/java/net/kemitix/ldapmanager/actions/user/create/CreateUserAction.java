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

package net.kemitix.ldapmanager.actions.user.create;

import lombok.RequiredArgsConstructor;
import net.kemitix.ldapmanager.domain.User;
import net.kemitix.ldapmanager.ldap.events.ContainerExpiredEvent;
import net.kemitix.ldapmanager.ui.CloseCenterFormEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.stereotype.Component;

import javax.naming.Name;
import javax.naming.ldap.LdapName;

/**
 * Create the User in the LDAP Server.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@RequiredArgsConstructor
@Component
class CreateUserAction {

    private final LdapTemplate ldapTemplate;

    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * Listener for {@link CreateUserCommitEvent} to create the user in LDAP.
     *
     * @param event the event
     */
    @EventListener(CreateUserCommitEvent.class)
    public final void onCreateUserCommit(final CreateUserCommitEvent event) {
        // create the user
        final Name ouDn = event.getDn();
        final String cn = event.getCn();
        final String sn = event.getSn();
        final LdapName userDn = LdapNameBuilder.newInstance(ouDn)
                                               .add("cn", cn)
                                               .build();
        final User user = User.builder()
                              .dn(userDn)
                              .cn(cn)
                              .sn(sn)
                              .build();
        ldapTemplate.create(user);
        // close form
        applicationEventPublisher.publishEvent(CloseCenterFormEvent.create());
        // force context update
        applicationEventPublisher.publishEvent(ContainerExpiredEvent.of(ouDn));
    }
}
