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

package net.kemitix.ldapmanager.popupmenus.insert;

import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import lombok.NonNull;
import lombok.val;
import net.kemitix.ldapmanager.popupmenus.MenuItemFactory;
import net.kemitix.ldapmanager.popupmenus.MenuItemReceiver;
import net.kemitix.ldapmanager.popupmenus.MenuItemType;
import net.kemitix.ldapmanager.popupmenus.PopupMenu;
import net.kemitix.ldapmanager.ui.ActionListDialogBuilderFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.naming.Name;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

/**
 * Popup Menu for inserting/creating items.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Component
class InsertMenu implements PopupMenu, MenuItemReceiver {

    private final WindowBasedTextGUI gui;

    private final ActionListDialogBuilderFactory dialogBuilderFactory;

    private final Set<MenuItemFactory> menuItemFactories;

    private final Set<MenuItemType> menuItemTypes;

    /**
     * Constructor.
     *
     * @param gui                  The GUI to display the menu.
     * @param dialogBuilderFactory The Action List Dialog Builder Factory.
     * @param menuItemFactories    The Factories for creating menu items.
     */
    InsertMenu(
            final WindowBasedTextGUI gui, final ActionListDialogBuilderFactory dialogBuilderFactory,
            final Set<MenuItemFactory> menuItemFactories
              ) {
        this.gui = gui;
        this.dialogBuilderFactory = dialogBuilderFactory;
        this.menuItemFactories = new HashSet<>(menuItemFactories);
        menuItemTypes = EnumSet.of(MenuItemType.CREATE);
    }

    /**
     * Listener for {@link DisplayInsertMenuEvent} to display the insert menu.
     *
     * @param event The event.
     */
    @EventListener(DisplayInsertMenuEvent.class)
    public final void onDisplayInsertMenu(final DisplayInsertMenuEvent event) {
        display(event.getDn(), "");
    }

    @Override
    public void display(final Name dn, final String title) {
        val dialogBuilder = dialogBuilderFactory.create();
        menuItemFactories.stream()
                         .flatMap(menuItemFactory -> menuItemFactory.create(dn, this))
                         .forEach(menuItem -> dialogBuilder.addAction(menuItem.getLabel(), menuItem.getAction()));
        if (!dialogBuilder.getActions()
                          .isEmpty()) {
                         .build()
                         .showDialog(gui);
            dialogBuilder.setTitle(title)
        }
    }

    @Override
    public boolean canReceive(@NonNull final MenuItemType type) {
        return menuItemTypes.contains(type);
    }
}
