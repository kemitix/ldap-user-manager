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

package net.kemitix.ldapmanager.events;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Log message added.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Component
public class LogMessageAdded {

    /**
     * Dispatches an event signaling that a new log message has been added.
     *
     * @param publisher The Application Event Publisher.
     *
     * @return the event dispatcher
     */
    @Bean
    @SuppressWarnings("designforextension")
    public EventDispatcher logMessageAddedDispatcher(final ApplicationEventPublisher publisher) {
        return () -> publisher.publishEvent(new Event(this));
    }

    /**
     * Listener for Log Message Added.
     */
    public interface Listener extends ApplicationListener<Event> {

    }

    /**
     * The Event signalling that a new log message has been added.
     */
    public static final class Event extends ApplicationEvent {

        /**
         * Create a new ApplicationEvent.
         *
         * @param source the object on which the event initially occurred (never {@code null})
         */
        public Event(final Object source) {
            super(source);
        }
    }
}
