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

package net.kemitix.ldapmanager.navigation;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.gui2.ActionListBox;
import com.googlecode.lanterna.gui2.Interactable;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import lombok.NonNull;
import lombok.extern.java.Log;
import lombok.val;
import net.kemitix.ldapmanager.events.CurrentContainerChangedEvent;
import net.kemitix.ldapmanager.Messages;
import net.kemitix.ldapmanager.ui.AbstractListenableListBox;
import net.kemitix.ldapmanager.ui.StartupExceptionsCollector;
import net.kemitix.ldapmanager.util.nameditem.NamedItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.ldap.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.logging.Level;

/**
 * An {@link ActionListBox} for listing {@link NavigationItem}s.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Log
@Component
class DefaultNavigationItemActionListBox
        extends AbstractListenableListBox<NavigationItem, DefaultNavigationItemActionListBox>
        implements NavigationItemActionListBox {

    private final Supplier<List<NamedItem<NavigationItem>>> navigationItemSupplier;

    private final StartupExceptionsCollector startupExceptionsCollector;

    private final ApplicationEventPublisher applicationEventPublisher;

    private boolean publishOnSelectionChange;

    /**
     * Constructor.
     *
     * @param navigationItemSupplier     The Supplier for Navigation Items.
     * @param startupExceptionsCollector The LDAP Server Status.
     * @param applicationEventPublisher  The Application Event Publisher.
     */
    @Autowired
    DefaultNavigationItemActionListBox(
            final Supplier<List<NamedItem<NavigationItem>>> navigationItemSupplier,
            final StartupExceptionsCollector startupExceptionsCollector,
            final ApplicationEventPublisher applicationEventPublisher
                                      ) {
        this.navigationItemSupplier = navigationItemSupplier;
        this.startupExceptionsCollector = startupExceptionsCollector;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     * Initializer to load the initial container.
     */
    @PostConstruct
    public final void init() {
        log.log(Level.FINEST, "init()");
        try {
            onCurrentContainerChanged();
        } catch (final AuthenticationException e) {
            startupExceptionsCollector.addException(Messages.ERROR_AUTHENTICATION, e);
        }
        publishOnSelectionChange = true;
    }

    @Override
    public final TerminalPosition getCursorLocation() {
        log.log(Level.FINEST, "getCursorLocation(): null");
        return null;
    }

    @Override
    public final Interactable.Result onHandleKeyStroke(final KeyStroke keyStroke) {
        log.log(Level.FINEST, "onHandleKeyStroke(%s)", keyStroke);
        final NavigationItem selectedItem = getSelectedItem();
        if (canHandle(keyStroke, selectedItem)) {
            selectedItem.run();
            return Interactable.Result.HANDLED;
        }
        return Interactable.Result.UNHANDLED;
    }

    @Override
    public final void onSelectionChange(final int oldSelectionIndex, final int newSelectionIndex) {
        log.log(Level.FINEST, "onSelectionChange(%d, %d)", new Object[]{oldSelectionIndex, newSelectionIndex});
        if (publishOnSelectionChange) {
            doPublishOnSelectionChange(oldSelectionIndex, newSelectionIndex);
        }
    }

    private void doPublishOnSelectionChange(final int oldSelectionIndex, final int newSelectionIndex) {
        NavigationItem oldItem = null;
        if ((oldSelectionIndex >= 0) && (getItemCount() > 0)) {
            oldItem = getItemAt(oldSelectionIndex);
        }
        NavigationItem newItem = null;
        if ((newSelectionIndex >= 0) && (getItemCount() > 0)) {
            newItem = getItemAt(newSelectionIndex);
        }
        applicationEventPublisher.publishEvent(NavigationItemSelectionChangedEvent.of(oldItem, newItem));
    }

    private static boolean canHandle(final KeyStroke keyStroke, final Object selectedItem) {
        val isItemSelected = selectedItem != null;
        if (isItemSelected) {
            val isEnter = keyStroke.getKeyType() == KeyType.Enter;
            val isSpaceBar = (keyStroke.getKeyType() == KeyType.Character) && (Messages.CHAR_SPACE.equals(
                    keyStroke.getCharacter()));
            return isEnter || isSpaceBar;
        }
        return false;
    }

    /**
     * Update the contents of the action list box with the contents of the current OU.
     */
    @EventListener(CurrentContainerChangedEvent.class)
    public final void onCurrentContainerChanged() {
        log.log(Level.FINEST, "onCurrentContainerChanged()");
        clearItems();
        navigationItemSupplier.get()
                              .forEach(item -> addItem(item.getItem()));
    }

    @Override
    public final Optional<NavigationItem> findAndSelectItemByName(@NonNull final String name) {
        log.log(Level.FINEST, "findAndSelectItemByName(%s)", name);
        return findItemPositionByName(name).map(pos -> {
            setSelectedIndex(pos);
            return getItemAt(pos);
        });
    }

    @Override
    public final Optional<Integer> findItemPositionByName(@NonNull final String name) {
        log.log(Level.FINEST, "findItemPositionByName(%s)", name);
        int selected = 0;
        for (final NavigationItem navigationItem : getItems()) {
            final String itemName = navigationItem.toString();
            if (name.equals(itemName)) {
                return Optional.of(selected);
            }
            selected++;
        }
        return Optional.empty();
    }

    @Override
    public final void performSelectedItem() {
        log.log(Level.FINEST, "performSelectedItem()");
        handleKeyStroke(new KeyStroke(KeyType.Enter));
    }

}
