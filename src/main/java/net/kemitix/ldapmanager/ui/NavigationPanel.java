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
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;
import lombok.extern.java.Log;
import net.kemitix.ldapmanager.navigation.NavigationItemActionListBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.logging.Level;

/**
 * The Left-hand Navigation Panel.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Log
@Component
class NavigationPanel extends Panel {

    private final NavigationItemActionListBox actionListBox;

    /**
     * Constructor.
     *
     * @param actionListBox The Navigation Item Action List Box.
     */
    @Autowired
    NavigationPanel(final NavigationItemActionListBox actionListBox) {
        super(new BorderLayout());
        this.actionListBox = actionListBox;
    }

    /**
     * Initializer.
     */
    @PostConstruct
    public final void init() {
        log.log(Level.FINEST, "init()");
        addComponent(new Panel().addComponent(actionListBox, LinearLayout.createLayoutData(LinearLayout.Alignment.Fill))
                                .withBorder(Borders.singleLine("Navigation")), BorderLayout.Location.CENTER);
    }
}
