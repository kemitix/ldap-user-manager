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

import com.googlecode.lanterna.gui2.BorderLayout;
import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LayoutData;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;
import lombok.val;
import net.kemitix.ldapmanager.Messages;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * The top panel of the UI, containing the current OU.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Component
class TopPanel extends Panel {

    private static final LayoutData FILL = LinearLayout.createLayoutData(LinearLayout.Alignment.Fill);

    private static final LayoutData RIGHT = BorderLayout.Location.RIGHT;

    private static final LayoutData CENTER = BorderLayout.Location.CENTER;

    private final Label currentOuLabel;

    private final Label clockLabel;

    /**
     * Constructor.
     *
     * @param currentOuLabel The current OU label
     * @param clockLabel     The clock label
     */
    TopPanel(final Label currentOuLabel, final Label clockLabel) {
        this.currentOuLabel = currentOuLabel;
        this.clockLabel = clockLabel;
    }

    /**
     * Initializer.
     */
    @PostConstruct
    public void init() {
        val ouPanel = new Panel().addComponent(currentOuLabel)
                                 .withBorder(Borders.singleLine(Messages.CURRENT_OU.getValue()));
        val clockPanel = new Panel().addComponent(clockLabel)
                                    .withBorder(Borders.singleLine(Messages.TIME.getValue()));
        val horizon = new Panel(new BorderLayout()).addComponent(ouPanel, CENTER)
                                                   .addComponent(clockPanel, RIGHT);
        addComponent(horizon, FILL);
    }
}
