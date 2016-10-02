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
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import net.kemitix.ldapmanager.state.LogMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * The Lanterna UI.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Profile("default")
@Component
class LanternaUi implements CommandLineRunner {

    private final LogMessages logMessages;

    private final BasicWindow mainWindow;

    private final WindowBasedTextGUI gui;

    /**
     * Constructor.
     *
     * @param logMessages The Log Messages
     * @param mainWindow  The main UI windows
     * @param gui         The Lanterna UI
     */
    @Autowired
    LanternaUi(
            final LogMessages logMessages, final BasicWindow mainWindow, final WindowBasedTextGUI gui
              ) {
        this.logMessages = logMessages;
        this.mainWindow = mainWindow;
        this.gui = gui;
    }

    /**
     * Display the Lanterna UI.
     *
     * @param args The command line arguments - ignored
     *
     * @throws IOException if there is an error starting the screen
     */
    @Override
    public void run(final String... args) throws IOException {
        logMessages.add("Starting Lanterna UI...adding main window");
        gui.addWindow(mainWindow);
        logMessages.add("Entering main loop...");
        gui.waitForWindowToClose(mainWindow);
    }
}
