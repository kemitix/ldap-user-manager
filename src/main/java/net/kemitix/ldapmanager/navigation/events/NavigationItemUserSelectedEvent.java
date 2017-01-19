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

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import net.kemitix.ldapmanager.navigation.UserNavigationItem;

import javax.naming.Name;
import java.util.logging.Level;

/**
 * Raised when a User Navigation Item is selected.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Log
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class NavigationItemUserSelectedEvent {

    @Getter
    private final UserNavigationItem userNavigationItem;

    @Getter
    private final Name dn;

    /**
     * Creates a new NavigationItemOuSelectedEvent.
     *
     * @param userNavigationItem the User Navigation Item selected
     *
     * @return the event
     */
    public static NavigationItemUserSelectedEvent of(@NonNull final UserNavigationItem userNavigationItem) {
        log.log(Level.FINEST, "of(%s)", userNavigationItem);
        return new NavigationItemUserSelectedEvent(userNavigationItem, userNavigationItem.getUser()
                                                                                         .getDn());
    }
}
