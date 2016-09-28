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

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.Component;
import com.googlecode.lanterna.gui2.DefaultWindowManager;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.WindowManager;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.TerminalFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.IOException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * Configuration for main Lanterna UI.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Configuration
class LanternaConfiguration {

    public static final int CORE_POOL_SIZE = 10;

    /**
     * The Lanterna Terminal Factory.
     *
     * @return the terminal factory
     */
    @Bean
    TerminalFactory terminalFactory() {
        return new DefaultTerminalFactory();
    }

    /**
     * The Lanternal Terminal.
     *
     * @param terminalFactory The Lanterna Terminal Factory
     *
     * @return the terminal
     *
     * @throws IOException if error creating the terminal
     */
    @Bean
    @Profile("default")
    Terminal terminal(final TerminalFactory terminalFactory) throws IOException {
        return terminalFactory.createTerminal();
    }

    /**
     * The Lanterna Terminal Screen.
     *
     * @param terminal The Lanterna Terminal
     *
     * @return the terminal screen
     *
     * @throws IOException if error creating or starting the terminal screen
     */
    @Bean
    TerminalScreen terminalScreen(final Terminal terminal) throws IOException {
        final TerminalScreen screen = new TerminalScreen(terminal);
        screen.startScreen();
        return screen;
    }

    /**
     * The Lanterna Window Manager.
     *
     * @return the window manager
     */
    @Bean
    WindowManager windowManager() {
        return new DefaultWindowManager();
    }

    /**
     * The Lanterna Background Component.
     *
     * @param backgroundColor the background color
     *
     * @return the background
     */
    @Bean
    Component background(final TextColor.ANSI backgroundColor) {
        return new EmptySpace(backgroundColor);
    }

    /**
     * The Background Color.
     *
     * @return the color
     */
    @Bean
    TextColor.ANSI backgroundColor() {
        return TextColor.ANSI.BLUE;
    }

    /**
     * The Lanterna Window GUI.
     *
     * @param screen        The Screen
     * @param windowManager The window manager
     * @param background    The background
     *
     * @return the gui
     */
    @Bean
    WindowBasedTextGUI gui(
            final Screen screen, final WindowManager windowManager, final Component background
                          ) {
        return new MultiWindowTextGUI(screen, windowManager, background);
    }

    /**
     * Executor Service for scheduled tasks.
     *
     * @return the executor service
     */
    @Bean
    ScheduledExecutorService scheduledExecutorService() {
        return new ScheduledThreadPoolExecutor(CORE_POOL_SIZE);
    }
}
