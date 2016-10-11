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

import lombok.Getter;
import net.kemitix.ldapmanager.handlers.KeyStrokeHandler;
import org.springframework.context.ApplicationEvent;

/**
 * Raised when a {@link KeyStrokeHandler} is enabled or disabled.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public final class KeyStrokeHandlerUpdateEvent extends ApplicationEvent {

    @Getter
    private final KeyStrokeHandler keyStrokeHandler;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the keystroke handler on which the event initially occurred (never {@code null})
     */
    private KeyStrokeHandlerUpdateEvent(final KeyStrokeHandler source) {
        super(source);
        this.keyStrokeHandler = source;
    }

    /**
     * Create a new KeyStrokeHandlerUpdateEvent.
     *
     * @param keyStrokeHandler The KeyStorkeHandler that triggered the update event
     *
     * @return the event
     */
    public static KeyStrokeHandlerUpdateEvent of(final KeyStrokeHandler keyStrokeHandler) {
        return new KeyStrokeHandlerUpdateEvent(keyStrokeHandler);
    }
}
