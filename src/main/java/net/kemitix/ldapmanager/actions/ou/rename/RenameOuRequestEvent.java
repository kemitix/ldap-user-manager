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

package net.kemitix.ldapmanager.actions.ou.rename;

import lombok.Getter;
import net.kemitix.ldapmanager.navigation.OuNavigationItem;

/**
 * Raised when the user wants to rename an {@link OuNavigationItem}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public final class RenameOuRequestEvent {

    @Getter
    private final OuNavigationItem ouNavigationItem;

    private RenameOuRequestEvent(final OuNavigationItem ouNavigationItem) {
        this.ouNavigationItem = ouNavigationItem;
    }

    /**
     * Create a new RenameOuRequestEvent.
     *
     * @param ouNavigationItem The OU Navigation Item to be renamed.
     *
     * @return the event
     */
    public static RenameOuRequestEvent create(final OuNavigationItem ouNavigationItem) {
        return new RenameOuRequestEvent(ouNavigationItem);
    }
}
