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

package net.kemitix.ldapmanager.actions.user.create;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Container;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LayoutData;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.val;
import net.kemitix.ldapmanager.events.ApplicationExitRequestEvent;
import net.kemitix.ldapmanager.events.CreateUserCommitEvent;
import net.kemitix.ldapmanager.events.CreateUserEvent;
import net.kemitix.ldapmanager.state.LogMessages;
import net.kemitix.ldapmanager.ui.Focusable;
import net.kemitix.ldapmanager.ui.FormContainer;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.naming.Name;

/**
 * Panel for form to create a new user.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Component
@RequiredArgsConstructor
class CreateUserFormPanel extends Panel implements Focusable {

    private static final LayoutData LAYOUT_TEXT_FIELD =
            GridLayout.createLayoutData(GridLayout.Alignment.FILL, GridLayout.Alignment.BEGINNING, true, false);

    private static final LayoutData LAYOUT_BUTTON =
            GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.END);

    private static final LayoutData LAYOUT_FIELD_LABEL =
            GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.BEGINNING);

    private final FormContainer formContainer;

    private final LogMessages logMessages;

    private final ApplicationEventPublisher applicationEventPublisher;

    private Container form;

    private Name dn;

    @Getter
    private TextBox cnTextBox;

    @Getter
    private TextBox snTextBox;

    /**
     * Initializer.
     */
    @PostConstruct
    public void init() {
        val layoutManager = new GridLayout(2);
        layoutManager.setHorizontalSpacing(1);
        layoutManager.setVerticalSpacing(1);
        val formPanel = new Panel(layoutManager);

        /// Row 1

        formPanel.addComponent(new Label("Common Name (cn):"), LAYOUT_FIELD_LABEL);
        cnTextBox = new TextBox("", TextBox.Style.SINGLE_LINE);
        formPanel.addComponent(cnTextBox, LAYOUT_TEXT_FIELD);

        /// Row 2

        formPanel.addComponent(new Label("Surname (sn):"), LAYOUT_FIELD_LABEL);
        snTextBox = new TextBox("", TextBox.Style.SINGLE_LINE);
        formPanel.addComponent(snTextBox, LAYOUT_TEXT_FIELD);

        /// Row 3

        formPanel.addComponent(new EmptySpace(new TerminalSize(0, 0)));
        val createButton = new Button("Create", this::onCreateButtonPressed);
        formPanel.addComponent(createButton, LAYOUT_BUTTON);

        ///
        form = formPanel;
    }

    /**
     * Presses the Create button to create a user.
     */
    public void onCreateButtonPressed() {
        final String cn = cnTextBox.getText();
        final String sn = snTextBox.getText();
        if (cn.isEmpty() || sn.isEmpty()) {
            return;
        }
        logMessages.add(String.format("Create user %s (%s) in %s", cn, sn, dn));
        applicationEventPublisher.publishEvent(CreateUserCommitEvent.of(dn, cn, sn));
    }

    /**
     * Listener for {@link CreateUserEvent} to display a form for creating a new user.
     *
     * @param event the event.
     */
    @EventListener(CreateUserEvent.class)
    public final void onCreateUserEvent(final CreateUserEvent event) {
        dn = event.getDn();
        cnTextBox.setText("");
        snTextBox.setText("");
        formContainer.replaceComponents(form.withBorder(Borders.doubleLine(String.format("Create User in %s", dn))));
        setFocused();
    }

    @Override
    public final void setFocused() {
        formContainer.getBasePane()
                     .setFocusedInteractable(cnTextBox);
    }

    /**
     * Listener for {@link ApplicationExitRequestEvent} to prevent the application from exiting if the form is open.
     *
     * <p>The form is considered 'open' is if is in the formContainer.</p>
     *
     * @param event The event
     */
    @EventListener(ApplicationExitRequestEvent.class)
    public void onApplicationExitRequest(final ApplicationExitRequestEvent event) {
        if (form.isInside(formContainer)) {
            event.deny();
        }
    }
}
