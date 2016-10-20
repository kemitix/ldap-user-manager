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
import net.kemitix.ldapmanager.popupmenus.MenuItemFactory;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

/**
 * Factory for creating {@link MenuItem}s to rename {@link NavigationItem}s.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Component
class RenameMenuItemFactory implements MenuItemFactory {

    @Override
    public final Stream<MenuItem> create(final NavigationItem navigationItem) {
        return Stream.of(new RenameMenuItemFactory.RenameMenuItem(navigationItem));
    }

    /**
     * Menu item for renaming a Navigation Item.
     */
    private static class RenameMenuItem implements MenuItem {

        private static final String LABEL = "Rename";

        private final NavigationItem navigationItem;

        RenameMenuItem(final NavigationItem navigationItem) {
            this.navigationItem = navigationItem;
        }

        @Override
        public final String getLabel() {
            return LABEL;
        }

        @Override
        public final Runnable getAction() {
            return navigationItem::publishRenameRequest;
        }
    }
}
