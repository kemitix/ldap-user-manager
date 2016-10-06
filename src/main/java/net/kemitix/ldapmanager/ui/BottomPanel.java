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

import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;
import lombok.Getter;
import lombok.val;
import net.kemitix.ldapmanager.events.ApplicationExitEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * The bottom panel of the UI, containing the exit button.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Component
class BottomPanel extends Panel {

    private final ApplicationEventPublisher eventPublisher;

    @Getter
    private final Button exitButton;

    /**
     * Constructor.
     *
     * @param eventPublisher The event publisher
     */
    @Autowired
    BottomPanel(final ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
        this.exitButton = new Button("Exit", sendApplicationExitEvent());
    }

    /**
     * Initializer.
     */
    @PostConstruct
    public void init() {
        val component = new Panel().addComponent(exitButton).withBorder(Borders.singleLine());
        component.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Fill));
        addComponent(component);
    }

    private Runnable sendApplicationExitEvent() {
        return () -> eventPublisher.publishEvent(new ApplicationExitEvent(this));
    }
}
