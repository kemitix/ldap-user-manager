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

package net.kemitix.ldapmanager.context;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import net.kemitix.ldapmanager.handlers.KeyStrokeHandler;
import net.kemitix.ldapmanager.navigation.ui.NavigationItemActionListBox;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * Display a context menu when user presses the Enter key.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Component
class ContextKeyStrokeHandler implements KeyStrokeHandler {

    private static final String DESCRIPTION = "Menu";

    private static final String KEY = "F2";

    private static final KeyType KEY_TYPE = KeyType.F2;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final NavigationItemActionListBox navigationItemActionListBox;

    /**
     * Constructor.
     *
     * @param applicationEventPublisher   The Application Event Publisher.
     * @param navigationItemActionListBox The Navigation Item Action List Box.
     */
    ContextKeyStrokeHandler(
            final ApplicationEventPublisher applicationEventPublisher,
            final NavigationItemActionListBox navigationItemActionListBox
                           ) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.navigationItemActionListBox = navigationItemActionListBox;
    }

    @Override
    public final boolean isActive() {
        return true;
    }

    @Override
    public final String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public final String getKey() {
        return KEY;
    }

    @Override
    public final boolean canHandleKey(final KeyStroke key) {
        return key.getKeyType() == KEY_TYPE;
    }

    @Override
    public final void handleInput(final KeyStroke key) {
        applicationEventPublisher.publishEvent(
                DisplayContextMenuEvent.of(navigationItemActionListBox.getSelectedItem()));
    }
}
