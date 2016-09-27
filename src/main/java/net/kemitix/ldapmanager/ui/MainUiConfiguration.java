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
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;
import lombok.val;
import net.kemitix.ldapmanager.ldap.LdapOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * UI configuration for the LDAP Manager.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Configuration
class MainUiConfiguration {

    /**
     * The top panel of the UI, containing the current OU.
     *
     * @param currentOuLabel The current OU Lable
     *
     * @return the top panel
     */
    @Bean
    public Panel topPanel(final Label currentOuLabel) {
        val component = new Panel().addComponent(currentOuLabel)
                                   .withBorder(Borders.singleLine("Current OU"));
        component.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Fill));
        val topPanel = new Panel().addComponent(component);
        topPanel.setLayoutData(BorderLayout.Location.TOP);
        return topPanel;
    }

    /**
     * The current OU Label.
     *
     * @param ldapOptions The LDAP server options
     *
     * @return the label
     */
    @Bean
    public Label currentOuLabel(final LdapOptions ldapOptions) {
        return new Label(ldapOptions.getBase());
    }

    /**
     * The bottom panel of the UI, containing the exit button.
     *
     * @param appExitHandler The exit handler
     *
     * @return the bottom panel
     */
    @Bean
    public Panel bottomPanel(final Runnable appExitHandler) {
        val component = new Panel().addComponent(new Button("Exit", appExitHandler))
                                   .withBorder(Borders.singleLine());
        component.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Fill));
        val bottomPanel = new Panel().addComponent(component);
        bottomPanel.setLayoutData(BorderLayout.Location.BOTTOM);
        return bottomPanel;
    }
}
