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

package net.kemitix.ldapmanager.actions.user.rename;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import lombok.Getter;
import lombok.val;
import net.kemitix.ldapmanager.domain.User;
import net.kemitix.ldapmanager.events.KeyStrokeHandlerUpdateEvent;
import net.kemitix.ldapmanager.handlers.KeyStrokeHandler;
import net.kemitix.ldapmanager.ldap.LdapService;
import net.kemitix.ldapmanager.navigation.UserNavigationItem;
import net.kemitix.ldapmanager.navigation.events.NavigationItemSelectionChangedEvent;
import net.kemitix.ldapmanager.navigation.events.NavigationItemUserSelectedEvent;
import net.kemitix.ldapmanager.ui.RenameDnDialog;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * {@link KeyStrokeHandler} for renaming {@link User} objects.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Component
class RenameUserKeyStrokeHandler implements KeyStrokeHandler {

    private static final char CHAR_R = 'r';

    private static final String DESCRIPTION = "Rename User";

    private static final String KEY = "r";

    private final ApplicationEventPublisher applicationEventPublisher;

    private UserNavigationItem lastSelectedUserNavigationItem;

    @Getter
    private boolean active;

    private final RenameDnDialog renameDnDialog;

    private final LdapService ldapService;

    /**
     * Constructor.
     *
     * @param applicationEventPublisher The Application Event Publisher.
     * @param renameDnDialog            The Dialog to get the new name.
     * @param ldapService               The LDAP Service.
     */
    RenameUserKeyStrokeHandler(
            final ApplicationEventPublisher applicationEventPublisher, final RenameDnDialog renameDnDialog,
            final LdapService ldapService
                              ) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.renameDnDialog = renameDnDialog;
        this.ldapService = ldapService;
    }

    @Override
    public final String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public final String getKey() {
        return KEY;
    }

    @Override
    public final boolean canHandleKey(final KeyStroke key) {
        return (key.getKeyType() == KeyType.Character) && key.getCharacter()
                                                             .equals(CHAR_R);
    }

    @Override
    public final void handleInput(final KeyStroke key) {
        applicationEventPublisher.publishEvent(RenameUserRequestEvent.create(lastSelectedUserNavigationItem));
    }

    /**
     * Listener for {@link RenameUserRequestEvent} to display the rename dialog and update the LDAP server.
     *
     * @param event the event
     */
    @EventListener(RenameUserRequestEvent.class)
    public final void onRenameUserRequest(final RenameUserRequestEvent event) {
        val user = event.getUserNavigationItem()
                        .getUser();
        renameDnDialog.getRenamedDn(user.getDn())
                      .ifPresent(dn -> ldapService.rename(user, dn));
    }

    /**
     * Listener for {@link NavigationItemUserSelectedEvent} to activate the listener and take note of the user selected.
     *
     * @param event the event
     */
    @EventListener(NavigationItemUserSelectedEvent.class)
    public final void onUserSelectedEvent(final NavigationItemUserSelectedEvent event) {
        lastSelectedUserNavigationItem = event.getUserNavigationItem();
        active = true;
        applicationEventPublisher.publishEvent(KeyStrokeHandlerUpdateEvent.of(this));
    }

    /**
     * Listener for {@link NavigationItemSelectionChangedEvent} to deactivate the listener.
     *
     * @param event the event
     */
    @EventListener(NavigationItemSelectionChangedEvent.class)
    public final void onSelectionChanged(final NavigationItemSelectionChangedEvent event) {
        active = false;
        applicationEventPublisher.publishEvent(KeyStrokeHandlerUpdateEvent.of(this));
    }
}
