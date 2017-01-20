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

package net.kemitix.ldapmanager.actions.rename;

import lombok.RequiredArgsConstructor;
import lombok.val;
import net.kemitix.ldapmanager.events.RenameRequestEvent;
import net.kemitix.ldapmanager.events.ContainerExpiredEvent;
import net.kemitix.ldapmanager.ui.RenameDnDialog;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Component;

import javax.naming.Name;

/**
 * Action for renaming an LdapEntity.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@RequiredArgsConstructor
@Component
class RenameAction {

    private final RenameDnDialog renameDnDialog;

    private final LdapTemplate ldapTemplate;

    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * Listener for {@link RenameRequestEvent} to display the rename dialog and update the LDAP server.
     *
     * @param event the event
     */
    @EventListener(RenameRequestEvent.class)
    public final void onRenameRequest(final RenameRequestEvent event) {
        val dn = event.getDn();
        renameDnDialog.getRenamedDn(dn)
                      .ifPresent(newDn -> rename(dn, newDn));
    }

    private void rename(final Name oldDn, final Name newDn) {
        ldapTemplate.rename(oldDn, newDn);
        applicationEventPublisher.publishEvent(ContainerExpiredEvent.containing(oldDn, newDn));
    }
}
