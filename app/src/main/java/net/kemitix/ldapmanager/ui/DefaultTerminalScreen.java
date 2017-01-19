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

import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.Terminal;
import net.kemitix.ldapmanager.events.ApplicationExitEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * The TerminalScreen.
 *
 * @author Paul Campbell (paul.campbell@hubio.com)
 */
@Component
public class DefaultTerminalScreen extends TerminalScreen {

    /**
     * Constructor.
     *
     * @param terminal The Terminal
     *
     * @throws IOException if there is an error creating the terminal screen
     */
    @Autowired
    public DefaultTerminalScreen(final Terminal terminal) throws IOException {
        super(terminal);
    }

    /**
     * Initializer.
     *
     * <p>Start the screen.</p>
     *
     * @throws IOException if error starting screen
     */
    @PostConstruct
    public final void init() throws IOException {
        startScreen();
    }

    /**
     * Listener to stop the screen.
     *
     * @throws IOException if error stopping terminal screen
     */
    @EventListener(ApplicationExitEvent.class)
    @Order(2)
    public final void onApplicationExit() throws IOException {
        stopScreen();
    }
}
