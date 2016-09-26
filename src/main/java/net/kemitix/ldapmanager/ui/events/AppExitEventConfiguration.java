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

package net.kemitix.ldapmanager.ui.events;

import com.googlecode.lanterna.gui2.BasicWindow;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Application Exit Event Configuration.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Configuration
class AppExitEventConfiguration {

    /**
     * The handler for the exit button, quiting the UI.
     *
     * @param publisher The application event publisher
     *
     * @return the runnable to perform when triggered
     */
    @Bean
    public Runnable appExitHandler(final ApplicationEventPublisher publisher) {
        return () -> publisher.publishEvent(new AppExitEvent(this));
    }

    /**
     * Application listener for the application exit event.
     *
     * @param window the main window
     *
     * @return the listener
     */
    @Bean
    public ApplicationListener<AppExitEvent> appExitListener(final BasicWindow window) {
        return e -> window.close();
    }

    /**
     * The Application Exit Event.
     */
    private static class AppExitEvent extends ApplicationEvent {

        AppExitEvent(final Object source) {
            super(source);
        }
    }
}
