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

package net.kemitix.ldapmanager.ui;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.input.KeyStroke;
import net.kemitix.ldapmanager.events.ApplicationExitEvent;
import net.kemitix.ldapmanager.state.KeyStrokeHandlers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Arrays;

/**
 * The main application window in the UI.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Component
class MainWindow extends BasicWindow {

    private final Panel mainPanel;

    private final ApplicationEventPublisher eventPublisher;

    private final KeyStrokeHandlers keyStrokeHandlers;

    /**
     * Constructor.
     *
     * @param mainPanel         The main panel
     * @param eventPublisher    The Application Event Publisher.
     * @param keyStrokeHandlers The KeyStroke Handlers
     */
    @Autowired
    MainWindow(
            final Panel mainPanel, final ApplicationEventPublisher eventPublisher,
            final KeyStrokeHandlers keyStrokeHandlers
              ) {
        this.mainPanel = mainPanel;
        this.eventPublisher = eventPublisher;
        this.keyStrokeHandlers = keyStrokeHandlers;
    }

    /**
     * Initializer.
     */
    @PostConstruct
    public void init() {
        setHints(Arrays.asList(Window.Hint.FULL_SCREEN, Window.Hint.NO_DECORATIONS));
        setComponent(mainPanel);
    }

    /**
     * Listener to close the main UI window.
     *
     * @throws IOException if error stopping terminal screen
     */
    @EventListener(ApplicationExitEvent.class)
    @Order(1)
    public void onApplicationExit() throws IOException {
        close();
    }

    @Override
    public boolean handleInput(final KeyStroke key) {
        return super.handleInput(key) || keyStrokeHandlers.handleInput(key);
    }
}
