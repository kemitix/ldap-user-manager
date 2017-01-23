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

package net.kemitix.ldapmanager.navigation.events;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import net.kemitix.ldapmanager.events.NavigationItemOuSelectedEvent;
import net.kemitix.ldapmanager.navigation.NavigationItem;
import net.kemitix.ldapmanager.state.LogMessages;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.logging.Level;

/**
 * Listens for changes in selected navigation items.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Log
@Component
@RequiredArgsConstructor
class SelectionChangeListener {

    private final LogMessages logMessages;

    /**
     * Listener for {@link NavigationItemSelectionChangedEvent} to have the newly selected item republish itself with an
     * appropriately typed event.
     *
     * @param event the event
     */
    @EventListener(NavigationItemSelectionChangedEvent.class)
    public final void onSelectionChanged(final NavigationItemSelectionChangedEvent event) {
        final String message = String.format("onSelectionChanged(%s -> %s)", event.getOldItem(), event.getNewItem());
        log.log(Level.FINEST, message);
        logMessages.add(message);
        event.getNewItem()
             .ifPresent(NavigationItem::publishAsSelected);
    }

    /**
     * Listener for {@link NavigationItemOuSelectedEvent} to simply log the selection of the OU.
     *
     * @param event the event
     */
    @EventListener(NavigationItemOuSelectedEvent.class)
    public final void onOuSelected(final NavigationItemOuSelectedEvent event) {
        final String message = String.format("onOuSelected(%s)", event.getDn());
        log.log(Level.FINEST, message);
        logMessages.add(message);
    }

    /**
     * Listener for {@link NavigationItemUserSelectedEvent} to simply log the selection of the User.
     *
     * @param event the event
     */
    @EventListener(NavigationItemUserSelectedEvent.class)
    public final void onUserSelected(final NavigationItemUserSelectedEvent event) {
        final String message = String.format("onUserSelected(%s)", event.getDn());
        log.log(Level.FINEST, message);
        logMessages.add(message);
    }
}
