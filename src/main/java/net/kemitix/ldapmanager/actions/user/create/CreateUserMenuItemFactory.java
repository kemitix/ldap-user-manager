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

import lombok.val;
import net.kemitix.ldapmanager.popupmenus.MenuItem;
import net.kemitix.ldapmanager.popupmenus.MenuItemFactory;
import net.kemitix.ldapmanager.popupmenus.MenuItemReceiver;
import net.kemitix.ldapmanager.popupmenus.MenuItemType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.naming.Name;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * Factory for creating {@link CreateUserMenuItem} objects.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Component
class CreateUserMenuItemFactory implements MenuItemFactory {

    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * Constructor.
     *
     * @param applicationEventPublisher The Application Event Publisher.
     */
    @Autowired
    CreateUserMenuItemFactory(final ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public Stream<MenuItem> create(final Name dn, final MenuItemReceiver menu) {
        val items = new ArrayList<MenuItem>();
        if (menu.canReceive(MenuItemType.CREATE)) {
            items.add(CreateUserMenuItem.create(dn, applicationEventPublisher));
        }
        return items.stream();
    }
}
