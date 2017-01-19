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

import net.kemitix.ldapmanager.events.ApplicationExitEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * The Default {@link java.util.concurrent.ScheduledThreadPoolExecutor}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Component
class DefaultScheduledExecutorService extends ScheduledThreadPoolExecutor {

    /**
     * Constructor.
     *
     * @param uiProperties The UI Properties.
     */
    @Autowired
    DefaultScheduledExecutorService(final UiProperties uiProperties) {
        super(uiProperties.getScheduledThreadPoolSize());
    }

    /**
     * Shutdown the scheduledExecutorService on application exit.
     */
    @EventListener(ApplicationExitEvent.class)
    public void onApplicationExit() {
        shutdown();
    }
}
