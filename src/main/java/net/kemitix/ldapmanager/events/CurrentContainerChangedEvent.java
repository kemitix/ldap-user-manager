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

import org.springframework.context.ApplicationEvent;

import javax.naming.Name;

/**
 * Notification of a change to the current container.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public final class CurrentContainerChangedEvent extends ApplicationEvent {

    /**
     * Constructor.
     *
     * @param newContainer The name of the new container.
     */
    private CurrentContainerChangedEvent(final Name newContainer) {
        super(newContainer);
    }

    /**
     * Creates a new {@link CurrentContainerChangedEvent}.
     *
     * @param newContainer The name of the new container.
     *
     * @return the event
     */
    public static CurrentContainerChangedEvent of(final Name newContainer) {
        return new CurrentContainerChangedEvent(newContainer);
    }
}
