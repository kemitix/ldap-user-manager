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
import com.googlecode.lanterna.gui2.LayoutData;
import com.googlecode.lanterna.gui2.LayoutManager;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;
import lombok.RequiredArgsConstructor;
import lombok.val;
import net.kemitix.ldapmanager.Messages;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * The main Panel for the LDAP Manager UI.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Component
@RequiredArgsConstructor
class MainPanel extends Panel {

    private static final LayoutData FILL = LinearLayout.createLayoutData(LinearLayout.Alignment.Fill);

    private final Panel topPanel;

    private final Panel bottomPanel;

    private final AbstractFocusablePanel navigationPanel;

    private final Panel logPanel;

    private final LayoutManager layoutManager = new BorderLayout();

    private final Panel centerPanel;

    /**
     * Initializer.
     */
    @PostConstruct
    public final void init() {
        val innerPanel = new Panel();
        innerPanel.setLayoutManager(layoutManager);
        innerPanel.setLayoutData(BorderLayout.Location.CENTER);
        innerPanel.addComponent(topPanel, BorderLayout.Location.TOP);
        innerPanel.addComponent(getBottomPanel(), BorderLayout.Location.BOTTOM);
        innerPanel.addComponent(navigationPanel, BorderLayout.Location.LEFT);
        innerPanel.addComponent(centerPanel);
        addComponent(innerPanel.withBorder(Borders.singleLine(Messages.APP_NAME.getValue())));
        setLayoutManager(layoutManager);
        setLayoutData(BorderLayout.Location.CENTER);
    }

    private Panel getBottomPanel() {
        return new Panel(new LinearLayout()).addComponent(logPanel, FILL)
                                            .addComponent(bottomPanel, FILL);
    }

    /**
     * Event listener for {@link CloseCenterFormEvent} for clear the center panel and set the input focus on the
     * navigation panel.
     */
    @EventListener(CloseCenterFormEvent.class)
    public final void onCloseCenterForm() {
        navigationPanel.setFocused();
        centerPanel.removeAllComponents();
    }
}
