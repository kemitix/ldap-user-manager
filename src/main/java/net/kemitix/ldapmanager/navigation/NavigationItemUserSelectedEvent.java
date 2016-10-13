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

import lombok.Getter;
import lombok.extern.java.Log;

import javax.naming.Name;
import java.util.logging.Level;

/**
 * Raised when a User Navigation Item is selected.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Log
public final class NavigationItemUserSelectedEvent {

    @Getter
    private final UserNavigationItem selected;

    @Getter
    private final Name dn;

    /**
     * Create a new ApplicationEvent.
     *
     * @param selected the object on which the event initially occurred (never {@code null})
     */
    private NavigationItemUserSelectedEvent(final UserNavigationItem selected) {
        this.selected = selected;
        dn = selected.getUser()
                     .getDn();
    }

    /**
     * Creates a new NavigationItemOuSelectedEvent.
     *
     * @param item the User Navigation Item selected
     *
     * @return the event
     */
    public static NavigationItemUserSelectedEvent of(final UserNavigationItem item) {
        log.log(Level.FINEST, "of(%s)", item);
        return new NavigationItemUserSelectedEvent(item);
    }
}
