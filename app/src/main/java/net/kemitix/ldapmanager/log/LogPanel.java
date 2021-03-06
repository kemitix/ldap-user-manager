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

package net.kemitix.ldapmanager.log;

import com.googlecode.lanterna.gui2.Border;
import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kemitix.ldapmanager.Messages;
import net.kemitix.ldapmanager.events.LogMessageAddedEvent;
import net.kemitix.ldapmanager.state.LogMessages;
import net.kemitix.ldapmanager.ui.UiProperties;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * UI Panel for displaying log messages.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Component
@RequiredArgsConstructor
class LogPanel extends Panel {

    private final LogMessages logMessages;

    @Getter
    private final Label messageLabel = new Label("");

    private final UiProperties uiProperties;

    private int linesToShow;

    @Getter
    private boolean visible;

    @Getter
    private Border label;

    /**
     * Initializer.
     */
    @PostConstruct
    public void init() {
        linesToShow = uiProperties.getLogLinesToShow();
        label = new Panel().addComponent(messageLabel)
                           .withBorder(Borders.singleLine(Messages.LOG.getValue()));
        label.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Fill));
    }

    /**
     * Update the portion of the log shown.
     */
    @EventListener(LogMessageAddedEvent.class)
    public void update() {
        if (visible) {
            final int logSize = logMessages.getMessageCount();
            final StringBuilder log = new StringBuilder(200);
            for (int i = logSize; i < linesToShow; i++) {
                log.append(appendNewLine(""));
            }
            logMessages.getMessages()
                       .skip(Math.max(logSize - linesToShow, 0))
                       .map(LogPanel::appendNewLine)
                       .forEach(log::append);
            messageLabel.setText(log.toString());
        }
        invalidate();
    }

    private static String appendNewLine(final String line) {
        return String.format("%s%n", line);
    }

    /**
     * Toggle the visibility of the log panel, alternating between showing or hiding it.
     */
    void toggleVisibility() {
        if (visible) {
            hide();
        } else {
            show();
        }
    }

    /**
     * Hide the Log Panel.
     */
    void hide() {
        visible = false;
        removeComponent(label);
        update();
    }

    /**
     * Show the Log Panel.
     */
    void show() {
        visible = true;
        addComponent(label);
        update();
    }
}
