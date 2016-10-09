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

package net.kemitix.ldapmanager.ui;

import com.googlecode.lanterna.gui2.ActionListBox;
import com.googlecode.lanterna.gui2.BorderLayout;
import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import lombok.NonNull;
import net.kemitix.ldapmanager.events.CurrentContainerChangedEvent;
import net.kemitix.ldapmanager.util.nameditem.NamedItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * The Left-hand Navigation Panel.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Component
class NavigationPanel extends Panel {

    private static final BorderLayout.Location CENTER = BorderLayout.Location.CENTER;

    private final Supplier<List<NamedItem<Runnable>>> navigationItemSupplier;

    private ActionListBox actionListBox;

    /**
     * Constructor.
     *
     * @param navigationItemsSupplier the supplier of navigation items
     */
    @Autowired
    NavigationPanel(final Supplier<List<NamedItem<Runnable>>> navigationItemsSupplier) {
        super(new BorderLayout());
        this.navigationItemSupplier = navigationItemsSupplier;
        actionListBox = new ActionListBox();
    }

    /**
     * Initializer.
     */
    @PostConstruct
    public void init() {
        populateActionListBox();
        addComponent(new Panel().addComponent(actionListBox)
                                .withBorder(Borders.singleLine("Navigation")), CENTER);
    }

    /**
     * Update the contents of the action list box with the contents of the current OU.
     */
    @EventListener(CurrentContainerChangedEvent.class)
    public void onCurrentContainerChangedEventUpdateNavigationItems() {
        populateActionListBox();
    }

    private void populateActionListBox() {
        actionListBox.clearItems();
        navigationItemSupplier.get()
                              .forEach(item -> actionListBox.addItem(item.getName(), item.getItem()));
    }

    /**
     * Searches the action list box for an action with the given name, selects it and returns it.
     *
     * <p>If no match is found then the currently selected item is left unchanged.</p>
     *
     * @param name the name of the action to find
     *
     * @return the action in an optional, or an empty optional if no matches found
     */
    public Optional<Runnable> findAndSelectItemByName(@NonNull final String name) {
        return findItemPositionByName(name).map(pos -> {
            actionListBox.setSelectedIndex(pos);
            return actionListBox.getItemAt(pos);
        });
    }

    /**
     * Searches the action list box for the first action with the given name and returns it's index position.
     *
     * @param name the name of the action to find.
     *
     * @return an optional containing the index, or empty if no matches found.
     */
    public Optional<Integer> findItemPositionByName(@NonNull final String name) {
        int selected = 0;
        for (Runnable runnable : actionListBox.getItems()) {
            if (name.equals(runnable.toString())) {
                return Optional.of(selected);
            }
            selected++;
        }
        return Optional.empty();
    }

    /**
     * Sends an ENTER keystroke to the action list box to trigger the currently selected item.
     */
    public void performSelectedItem() {
        actionListBox.handleKeyStroke(new KeyStroke(KeyType.Enter));
    }
}
