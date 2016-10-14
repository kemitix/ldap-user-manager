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

package net.kemitix.ldapmanager.navigation;

import lombok.Getter;
import lombok.extern.java.Log;
import net.kemitix.ldapmanager.domain.OU;
import org.springframework.context.ApplicationEventPublisher;

import java.util.logging.Level;

/**
 * Navigation Item for an OU.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Log
public final class OuNavigationItem extends AbstractNavigationItem {

    @Getter
    private final OU ou;

    private final ApplicationEventPublisher applicationEventPublisher;

    private OuNavigationItem(final OU ou, final ApplicationEventPublisher applicationEventPublisher) {
        super(ou.getOu());
        this.ou = ou;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     * Create an OuNavigationItem.
     *
     * @param ou             The OU.
     * @param eventPublisher The Application Event Publisher.
     *
     * @return The ou navigation item.
     */
    public static OuNavigationItem create(final OU ou, final ApplicationEventPublisher eventPublisher) {
        log.log(Level.FINEST, "create(%s, ...)", ou.name());
        return new OuNavigationItem(ou, eventPublisher);
    }

    @Override
    public void run() {
        log.log(Level.FINEST, "run(): %1", getName());
        applicationEventPublisher.publishEvent(NavigationItemOuActionEvent.of(ou));
    }

    @Override
    public String toString() {
        return ou.name();
    }

    @Override
    public void publishAsSelected() {
        log.log(Level.FINEST, "publishAsSelected(): %1", getName());
        applicationEventPublisher.publishEvent(NavigationItemOuSelectedEvent.of(this));
    }
}
