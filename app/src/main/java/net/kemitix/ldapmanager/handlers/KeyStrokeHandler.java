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

import java.util.Optional;

/**
 * Handles when a keystroke is made.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public interface KeyStrokeHandler {

    /**
     * Checks of the handler is active or not.
     *
     * @return true if the handler is active
     */
    boolean isActive();

    /**
     * Returns the prompt for the handler.
     *
     * <p>This is the prompt for the user as to what key to press to get what result. e.g. '[esc] exit'</p>
     *
     * @return the prompt
     */
    default Optional<String> getPrompt() {
        return Optional.of(String.format("[%s] %s", getKey(), getDescription()));
    }

    /**
     * Returns a short description of what the handler will do.
     *
     * @return the short description
     */
    String getDescription();

    /**
     * Returns what key to press to active the handler.
     *
     * <p>e.g. 'esc'  for the escape key</p>
     *
     * @return the key
     */
    String getKey();

    /**
     * Checks if the handler can handle the key stroke.
     *
     * @param key The KeyStroke
     *
     * @return true if the handler can handle it, or false
     */
    boolean canHandleKey(KeyStroke key);

    /**
     * Handle the key stroke.
     *
     * @param key The KeyStroke
     */
    void handleInput(KeyStroke key);
}
