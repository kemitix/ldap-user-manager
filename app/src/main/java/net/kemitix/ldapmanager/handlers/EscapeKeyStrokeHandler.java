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

package net.kemitix.ldapmanager.handlers;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import lombok.RequiredArgsConstructor;
import lombok.val;
import net.kemitix.ldapmanager.events.ApplicationExitEvent;
import net.kemitix.ldapmanager.events.ApplicationExitRequestEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * Handles when the user pressed Escape by exiting the application.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Component
@RequiredArgsConstructor
class EscapeKeyStrokeHandler implements KeyStrokeHandler {

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public String getDescription() {
        return "Exit";
    }

    @Override
    public String getKey() {
        return "esc";
    }

    @Override
    public boolean canHandleKey(final KeyStroke key) {
        return KeyType.Escape.equals(key.getKeyType());
    }

    @Override
    public void handleInput(final KeyStroke key) {
        val exitRequestEvent = ApplicationExitRequestEvent.create();
        eventPublisher.publishEvent(exitRequestEvent);
        if (exitRequestEvent.isApproved()) {
            eventPublisher.publishEvent(ApplicationExitEvent.create());
        }
    }
}
