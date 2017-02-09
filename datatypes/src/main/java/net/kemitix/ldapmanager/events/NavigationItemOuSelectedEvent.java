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

package net.kemitix.ldapmanager.events;

import net.kemitix.ldapmanager.navigation.OuNavigationItem;
import org.immutables.value.Value;

import javax.naming.Name;

/**
 * Raised when an OU Navigation Item is selected.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Value.Immutable
public interface NavigationItemOuSelectedEvent {

    /**
     * Get the OU Navigation Item selected.
     *
     * @return The OU Navigation Item selected
     */
    @Value.Parameter
    OuNavigationItem getOuNavigationItem();

    /**
     * The DN of the Navigation Item's OU.
     *
     * @return The DN
     */
    default Name getDn() {
        return getOuNavigationItem().getOu()
                                    .getDn();
    }

    /**
     * Creates a new NavigationItemOuSelectedEvent.
     *
     * @param ouNavigationItem The selected item
     *
     * @return The Event.
     */
    static NavigationItemOuSelectedEvent of(OuNavigationItem ouNavigationItem) {
        return ImmutableNavigationItemOuSelectedEvent.of(ouNavigationItem);
    }
}
