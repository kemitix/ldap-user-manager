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

package net.kemitix.ldapmanager.state;

import com.googlecode.lanterna.input.KeyStroke;
import lombok.val;
import net.kemitix.ldapmanager.handlers.KeyStrokeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * Default implementation of {@link KeyStrokeHandlers}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Component
class DefaultKeyStrokeHandlers implements KeyStrokeHandlers {

    private final Set<KeyStrokeHandler> keyStrokeHandlers;

    /**
     * Constructor.
     *
     * @param keyStrokeHandlers the key stroke handlers
     */
    @Autowired
    DefaultKeyStrokeHandlers(final Set<KeyStrokeHandler> keyStrokeHandlers) {
        this.keyStrokeHandlers = new HashSet<>(keyStrokeHandlers);
    }

    public Set<KeyStrokeHandler> getKeyStrokeHandlers() {
        return new HashSet<>(keyStrokeHandlers);
    }

    @Override
    public boolean handleInput(final KeyStroke key) {
        val keyStrokeHandler = keyStrokeHandlers.stream()
                                                .filter(KeyStrokeHandler::isActive)
                                                .filter(handler -> handler.canHandleKey(key))
                                                .sorted()
                                                .findFirst();
        keyStrokeHandler.ifPresent(handler -> handler.handleInput(key));
        return keyStrokeHandler.isPresent();
    }
}
