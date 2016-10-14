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

package net.kemitix.ldapmanager;

import lombok.Getter;

/**
 * Text messages.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@SuppressWarnings("enumvaluename")
public enum Messages {

    ERROR_AUTHENTICATION("Authentication error"),
    ERROR_UNHANDLED("Unhandled Error"),
    CURRENT_OU("Current OU"),
    TIME("Time"),
    NAVIGATION("Navigation"),
    APP_NAME("LDAP User Manager"),
    LOG("Log"),
    STARTING_LANTERNA_UI("Starting Lanterna UI...adding main window"),
    ENTERING_MAIN_LOOP("Entering main loop..."),
    LOG_CHANGED_TO_CONTAINER("Changed to container: "),
    KEYSTROKE_LABEL_DELIMITER(" | ");

    @Getter
    private final String value;

    /**
     * Constructor.
     *
     * @param value The string value of the message.
     */
    Messages(final String value) {
        this.value = value;
    }
}
