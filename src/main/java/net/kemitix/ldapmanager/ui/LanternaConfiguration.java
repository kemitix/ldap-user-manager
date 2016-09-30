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
import com.googlecode.lanterna.gui2.BasicWindow;
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
import net.kemitix.ldapmanager.ui.events.AppExitEvent;
import net.kemitix.ldapmanager.util.nameditem.NamedItem;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.function.Supplier;

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
    @Profile("default")
    ScheduledExecutorService scheduledExecutorService() {
        return new ScheduledThreadPoolExecutor(CORE_POOL_SIZE);
    }

    /**
     * Listener to shutdown the scheduledExecutorService on application exit.
     *
     * @param scheduledExecutorService The scheduled executor service
     *
     * @return the listener
     */
    @Bean
    public ApplicationListener<AppExitEvent> scheduledExecutorServiceAppExitListener(
            final ScheduledExecutorService scheduledExecutorService
                                                                                    ) {
        return e -> scheduledExecutorService.shutdown();
    }

    /**
     * Listener to close the main UI window.
     *
     * @param mainWindow The main window
     *
     * @return the listener
     */
    @Bean
    public ApplicationListener<AppExitEvent> mainWindowAppExitListener(final BasicWindow mainWindow) {
        return e -> mainWindow.close();
    }

    /**
     * Supplier of the current time.
     *
     * @return a Supplier for the current time
     */
    @Bean
    public Supplier<String> timeSupplier() {
        return () -> LocalTime.now()
                              .format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM));
    }

    /**
     * Supplier of navigation action items.
     *
     * @return the supplier of navigation items
     */
    @Bean
    public Supplier<List<NamedItem<Runnable>>> navigationItemSupplier() {
        final List<NamedItem<Runnable>> list = new ArrayList<>();
        list.add(NamedItem.of("item 1 - alpha", () -> {
        }));
        list.add(NamedItem.of("item 2 - bravo", () -> {
        }));
        list.add(NamedItem.of("item 3 - charlie", () -> {
        }));
        list.add(NamedItem.of("item 4 - delta", () -> {
        }));
        return () -> list;
    }
}
