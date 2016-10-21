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

package net.kemitix.ldapmanager.popupmenus.context;

import net.kemitix.ldapmanager.navigation.NavigationItem;
import net.kemitix.ldapmanager.popupmenus.MenuItem;

/**
 * Menu item for renaming a Navigation Item.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
final class RenameMenuItem implements MenuItem {

    private static final String LABEL = "Rename";

    private final NavigationItem navigationItem;

    private RenameMenuItem(final NavigationItem navigationItem) {
        this.navigationItem = navigationItem;
    }

    /**
     * Create a new RenameMenuUtem.
     *
     * @param navigationItem The NavigationItem to rename.
     *
     * @return the menu item.
     */
    public static RenameMenuItem create(final NavigationItem navigationItem) {
        return new RenameMenuItem(navigationItem);
    }

    @Override
    public String getLabel() {
        return LABEL;
    }

    @Override
    public Runnable getAction() {
        return navigationItem::publishRenameRequest;
    }
}
