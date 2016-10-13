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

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.java.Log;
import org.springframework.context.ApplicationEventPublisher;

/**
 * An abstract {@link NavigationItem}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Log
abstract class AbstractNavigationItem implements NavigationItem {

    @Getter(AccessLevel.PROTECTED)
    private final String name;

    @Getter(AccessLevel.PROTECTED)
    private final ApplicationEventPublisher eventPublisher;

    /**
     * Constructor.
     *
     * @param name           The name.
     * @param eventPublisher The Application Event Publisher.
     */
    protected AbstractNavigationItem(final String name, final ApplicationEventPublisher eventPublisher) {
        this.name = name;
        this.eventPublisher = eventPublisher;
    }
}
