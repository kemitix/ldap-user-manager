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

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import net.kemitix.ldapmanager.popupmenus.MenuItem;
import org.springframework.context.ApplicationEventPublisher;

import javax.naming.Name;

/**
 * Menu Item for creating a User.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
final class CreateUserMenuItem implements MenuItem {

    private static final String LABEL = "Create User";

    private final Name dn;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public String getLabel() {
        return LABEL;
    }

    @Override
    public Runnable getAction() {
        return () -> applicationEventPublisher.publishEvent(CreateUserEvent.create(dn));
    }

    /**
     * Create a new CreateUserMenuItem.
     *
     * @param dn                        The DN of the container where the new user should be created.
     * @param applicationEventPublisher The Application Event Publisher.
     *
     * @return the menu item.
     */
    public static CreateUserMenuItem create(final Name dn, final ApplicationEventPublisher applicationEventPublisher) {
        return new CreateUserMenuItem(dn, applicationEventPublisher);
    }
}
