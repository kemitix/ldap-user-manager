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

import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.TextInputDialog;
import com.googlecode.lanterna.gui2.dialogs.TextInputDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.TextInputDialogResultValidator;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Default implementation of factory for creating {@link TextInputDialog} objects.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Component
class DefaultTextInputDialogFactory implements TextInputDialogFactory {

    private final WindowBasedTextGUI gui;

    /**
     * Constructor.
     *
     * @param gui The Gui to display the dialog
     */
    @Autowired
    DefaultTextInputDialogFactory(final WindowBasedTextGUI gui) {
        this.gui = gui;
    }

    @Override
    public final String getInput(final TextInputDialog dialog) {
        return dialog.showDialog(gui);
    }

    @Override
    public final TextInputDialog create(
            @NonNull final String initialContent, final TextInputDialogResultValidator validator
                                       ) {
        final TextInputDialogBuilder dialog = builder().setInitialContent(initialContent);
        if (validator != null) {
            dialog.setValidator(validator);
        }
        return dialog.build();
    }

    @Override
    public final TextInputDialogBuilder builder() {
        return new TextInputDialogBuilder();
    }
}
