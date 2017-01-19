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
import com.googlecode.lanterna.gui2.Panel;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * The center of the main panel.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Component
class CenterPanel extends AbstractFocusablePanel implements FormContainer {

    /**
     * Initializer.
     */
    @PostConstruct
    public final void init() {
        setLayoutManager(new BorderLayout());
        setLayoutData(BorderLayout.Location.CENTER);
    }

    @Override
    public final Panel replaceComponents(final com.googlecode.lanterna.gui2.Component component) {
        return removeAllComponents().addComponent(component);
    }

    @Override
    public final void setFocused() {
        getChildren().stream()
                     .filter(component -> component instanceof Focusable)
                     .map(panel -> (Focusable) panel)
                     .findFirst()
                     .ifPresent(Focusable::setFocused);
    }
}
