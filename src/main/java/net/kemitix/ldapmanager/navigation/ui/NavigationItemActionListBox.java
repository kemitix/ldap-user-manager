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

package net.kemitix.ldapmanager.navigation.ui;

import com.googlecode.lanterna.gui2.Interactable;
import com.googlecode.lanterna.input.KeyStroke;
import lombok.NonNull;
import net.kemitix.ldapmanager.navigation.NavigationItem;

import java.util.Optional;

/**
 * An {@link com.googlecode.lanterna.gui2.ActionListBox} for listing {@link NavigationItem}s.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public interface NavigationItemActionListBox extends Interactable {

    /**
     * Handle keyboard input.
     *
     * @param keyStroke The key stroke from the user
     *
     * @return The result of processing the keystroke
     */
    Interactable.Result handleKeyStroke(KeyStroke keyStroke);

    /**
     * Searches the action list box for an action with the given name, selects it and returns it.
     *
     * <p>If no match is found then the currently selected item is left unchanged.</p>
     *
     * @param name the name of the action to find
     *
     * @return the action in an optional, or an empty optional if no matches found
     */
    Optional<NavigationItem> findAndSelectItemByName(@NonNull String name);

    /**
     * Searches the action list box for the first action with the given name and returns it's index position.
     *
     * @param name the name of the action to find.
     *
     * @return an optional containing the index, or empty if no matches found.
     */
    Optional<Integer> findItemPositionByName(@NonNull String name);

    /**
     * Sends an ENTER keystroke to the action list box to trigger the currently selected item.
     */
    void performSelectedItem();

    /**
     * Returns the index of the currently selected item in the list box. Please note that in this context, selected
     * simply means it is the item that currently has input focus. This is not to be confused with list box
     * implementations such as {@code CheckBoxList} where individual items have a certain checked/unchecked state.
     *
     * @return The index of the currently selected row in the list box, or -1 if there are no items
     */
    int getSelectedIndex();

    /**
     * Returns the currently selected item in the list box. Please note that in this context, selected
     * simply means it is the item that currently has input focus. This is not to be confused with list box
     * implementations such as {@code CheckBoxList} where individual items have a certain checked/unchecked state.
     *
     * @return The currently selected item in the list box, or {@code null} if there are no items
     */
    NavigationItem getSelectedItem();
}
