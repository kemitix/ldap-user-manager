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

import com.googlecode.lanterna.gui2.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

/**
 * A label that holds the current time.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Component
class ClockLabel extends Label {

    private static final long ONE_SECOND = 1000L;

    private final Supplier<String> timeSupplier;

    /**
     * Constructor.
     *
     * @param timeSupplier The supplier of the time
     */
    @Autowired
    ClockLabel(final Supplier<String> timeSupplier) {
        super("");
        this.timeSupplier = timeSupplier;
    }

    /**
     * Update the time in the label once a second.
     */
    @Scheduled(fixedRate = ONE_SECOND)
    public void updateLabel() {
        setText(getTime());
    }

    private String getTime() {
        return timeSupplier.get();
    }
}
