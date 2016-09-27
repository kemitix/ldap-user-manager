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
import com.googlecode.lanterna.gui2.LayoutManager;
import com.googlecode.lanterna.gui2.Panel;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * The main Panel for the LDAP Manager UI.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Component
class MainPanel extends Panel {

    private final Panel topPanel;

    private final Panel bottomPanel;

    private final LayoutManager layoutManager = new BorderLayout();

    /**
     * Constructor.
     *
     * @param topPanel    The top panel
     * @param bottomPanel The bottom panel
     */
    @Autowired
    MainPanel(final Panel topPanel, final Panel bottomPanel) {
        this.topPanel = topPanel;
        this.bottomPanel = bottomPanel;
    }

    /**
     * Initializer.
     */
    @PostConstruct
    public void init() {
        val innerPanel = new Panel();
        innerPanel.setLayoutManager(layoutManager);
        innerPanel.setLayoutData(BorderLayout.Location.CENTER);
        innerPanel.addComponent(topPanel);
        innerPanel.addComponent(bottomPanel);
        addComponent(innerPanel.withBorder(Borders.singleLine("LDAP User Manager")));
        setLayoutManager(layoutManager);
        setLayoutData(BorderLayout.Location.CENTER);
    }
}
