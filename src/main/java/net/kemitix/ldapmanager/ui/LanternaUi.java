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
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import lombok.extern.java.Log;
import net.kemitix.ldapmanager.LdapUserManagerException;
import net.kemitix.ldapmanager.Messages;
import net.kemitix.ldapmanager.events.ApplicationExitEvent;
import net.kemitix.ldapmanager.state.LogMessages;
import org.apache.commons.lang.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;

/**
 * The Lanterna UI.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Log
@Profile("default")
@Component
class LanternaUi implements CommandLineRunner {

    private static final String NEWLINE = System.getProperty("line.separator");

    private final LogMessages logMessages;

    private final BasicWindow mainWindow;

    private final WindowBasedTextGUI gui;

    private final ApplicationEventPublisher eventPublisher;

    private final MessageDialogBuilder messageDialogBuilder;

    private final StartupExceptionsCollector startupExceptionsCollector;

    /**
     * Constructor.
     *
     * @param logMessages                The Log Messages
     * @param mainWindow                 The main UI windows
     * @param gui                        The Lanterna UI
     * @param eventPublisher             The Application Event Publisher
     * @param messageDialogBuilder       The Message Dialog Builder
     * @param startupExceptionsCollector The LDAP Server Status.
     */
    @Autowired
    LanternaUi(
            final LogMessages logMessages, final BasicWindow mainWindow, final WindowBasedTextGUI gui,
            final ApplicationEventPublisher eventPublisher, final MessageDialogBuilder messageDialogBuilder,
            final StartupExceptionsCollector startupExceptionsCollector
              ) {
        this.logMessages = logMessages;
        this.mainWindow = mainWindow;
        this.gui = gui;
        this.eventPublisher = eventPublisher;
        this.messageDialogBuilder = messageDialogBuilder;
        this.startupExceptionsCollector = startupExceptionsCollector;
    }

    /**
     * Display the Lanterna UI.
     *
     * @param strings The command line arguments - ignored
     */
    @Override
    public final void run(final String... strings) {
        log.log(Level.FINEST, "run(%1)", strings);
        try {
            logMessages.add(Messages.STARTING_LANTERNA_UI.getValue());
            gui.addWindow(mainWindow);
            if (startupExceptionsCollector.isEmpty()) {
                logMessages.add(Messages.ENTERING_MAIN_LOOP.getValue());
                gui.waitForWindowToClose(mainWindow);
            }
            startupExceptionsCollector.getExceptions()
                                      .findFirst()
                                      .ifPresent(e -> exceptionMessageDialog(e).showDialog(gui));
        } catch (final LdapUserManagerException e) {
            exceptionMessageDialog(e).showDialog(gui);
        } finally {
            eventPublisher.publishEvent(ApplicationExitEvent.create());
        }

    }

    private MessageDialog exceptionMessageDialog(final LdapUserManagerException exception) {
        final Collection<String> messages = new ArrayList<>();
        Throwable tip = exception;
        while (tip != null) {
            messages.add(tip.getMessage());
            tip = tip.getCause();
        }
        final int wrapLength = gui.getScreen()
                                  .getTerminalSize()
                                  .getColumns() - 5;
        return messageDialogBuilder.setTitle(Messages.ERROR_UNHANDLED.getValue())
                                   .setText(WordUtils.wrap(String.join(NEWLINE, messages), wrapLength, NEWLINE, false))
                                   .build();
    }
}
