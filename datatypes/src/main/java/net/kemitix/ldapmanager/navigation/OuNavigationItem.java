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

import lombok.NonNull;
import net.kemitix.ldapmanager.domain.Features;
import net.kemitix.ldapmanager.domain.OU;
import net.kemitix.ldapmanager.events.NavigationItemOuActionEvent;
import net.kemitix.ldapmanager.events.NavigationItemOuSelectedEvent;
import net.kemitix.ldapmanager.events.RenameRequestEvent;
import org.immutables.value.Value;
import org.springframework.context.ApplicationEventPublisher;

/**
 * Navigation Item for an OU.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Value.Immutable
public interface OuNavigationItem extends NamedNavigationItem {

    /**
     * Gets the OU.
     *
     * @return The OU.
     */
    OU getOu();

    /**
     * Gets the Application Event Publisher.
     *
     * @return The Application Event Publisher
     */
    ApplicationEventPublisher getApplicationEventPublisher();

    /**
     * Creates a builder.
     *
     * @return the builder
     */
    static ImmutableOuNavigationItem.Builder builder() {
        return ImmutableOuNavigationItem.builder();
    }

    /**
     * Create an OuNavigationItem.
     *
     * @param ou             The OU.
     * @param eventPublisher The Application Event Publisher.
     *
     * @return The ou navigation item.
     */
    static OuNavigationItem create(@NonNull final OU ou, @NonNull final ApplicationEventPublisher eventPublisher) {
        return builder().ou(ou)
                        .name(ou.getOu())
                        .dn(ou.getDn())
                        .applicationEventPublisher(eventPublisher)
                        .build();
    }

    @Override
    default void run() {
        getApplicationEventPublisher().publishEvent(NavigationItemOuActionEvent.of(getOu()));
    }

    @Override
    default void publishAsSelected() {
        getApplicationEventPublisher().publishEvent(NavigationItemOuSelectedEvent.of(this));
    }

    @Override
    default void publishRenameRequest() {
        getApplicationEventPublisher().publishEvent(RenameRequestEvent.of(getDn()));
    }

    @Override
    default void publishChangePasswordRequest() {
        // does not support passwords
    }

    @Override
    default boolean hasFeature(@NonNull final Features feature) {
        return getOu().hasFeature(feature);
    }

    @Override
    default String getSortableType() {
        return "1:ou";
    }
}
