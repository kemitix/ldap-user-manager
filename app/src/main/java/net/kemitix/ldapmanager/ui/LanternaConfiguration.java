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

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.Component;
import com.googlecode.lanterna.gui2.DefaultWindowManager;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.WindowManager;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.TextInputDialogResultValidator;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.TerminalFactory;
import net.kemitix.ldapmanager.Messages;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for main Lanterna UI.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@SuppressWarnings({"DesignForExtension", "MethodMayBeStatic"})
@Configuration
class LanternaConfiguration {

    /**
     * The Lanterna Terminal Factory.
     *
     * @return the terminal factory
     */
    @Bean
    TerminalFactory terminalFactory() {
        return new DefaultTerminalFactory();
    }

    /**
     * The Lanterna Window Manager.
     *
     * @return the window manager
     */
    @Bean
    WindowManager windowManager() {
        return new DefaultWindowManager();
    }

    /**
     * The Lanterna Background Component.
     *
     * @param backgroundColor the background color
     *
     * @return the background
     */
    @Bean
    Component background(final TextColor.ANSI backgroundColor) {
        return new EmptySpace(backgroundColor);
    }

    /**
     * The Background Color.
     *
     * @return the color
     */
    @Bean
    TextColor.ANSI backgroundColor() {
        return TextColor.ANSI.BLUE;
    }

    /**
     * The Lanterna Window GUI.
     *
     * @param screen        The Screen
     * @param windowManager The window manager
     * @param background    The background
     *
     * @return the gui
     */
    @Bean
    WindowBasedTextGUI gui(
            final Screen screen, final WindowManager windowManager, final Component background
                          ) {
        return new MultiWindowTextGUI(screen, windowManager, background);
    }

    /**
     * The Lanterna Message Dialog Builder.
     *
     * @return the message dialog builder
     */
    @Bean
    MessageDialogBuilder messageDialogBuilder() {
        return new MessageDialogBuilder();
    }

    /**
     * Validator for TextInputDialogs to ensure that the value is not empty.
     *
     * @return the validator
     */
    @SuppressWarnings("ReturnOfNull")
    @Bean
    public TextInputDialogResultValidator validateNotEmpty() {
        return content -> {
            if (content.isEmpty()) {
                return Messages.ERROR_IS_EMPTY.getValue();
            }
            return null;
        };
    }
}
