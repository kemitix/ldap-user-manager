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
import net.kemitix.ldapmanager.domain.User;
import net.kemitix.ldapmanager.events.ChangePasswordRequestEvent;
import net.kemitix.ldapmanager.events.NavigationItemUserActionEvent;
import net.kemitix.ldapmanager.events.NavigationItemUserSelectedEvent;
import net.kemitix.ldapmanager.events.RenameRequestEvent;
import org.immutables.value.Value;
import org.springframework.context.ApplicationEventPublisher;

import javax.naming.Name;

/**
 * Navigation Item for a User.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Value.Immutable(builder = false)
@SuppressWarnings("abstractclassname")
public interface UserNavigationItem extends NavigationItem {

    @Override
    @Value.Derived
    default Name getDn() {
        return getUser().getDn();
    }

    /**
     * Gets the User.
     *
     * @return The User.
     */
    @Value.Parameter
    User getUser();

    /**
     * Gets the Application Event Publisher.
     *
     * @return The Application Event Publisher
     */
    @Value.Parameter
    ApplicationEventPublisher getApplicationEventPublisher();

    @Override
    default String getName() {
        return getUser().name();
    }

    /**
     * Creates a new UserNavigationItem.
     *
     * @param user           The user.
     * @param eventPublisher The Event Publisher.
     *
     * @return The navigation item.
     */
    static UserNavigationItem of(@NonNull final User user, final ApplicationEventPublisher eventPublisher) {
        return ImmutableUserNavigationItem.of(user, eventPublisher);
    }

    @Override
    default void run() {
        getApplicationEventPublisher().publishEvent(NavigationItemUserActionEvent.of(getUser()));
    }

    @Override
    default void publishAsSelected() {
        getApplicationEventPublisher().publishEvent(NavigationItemUserSelectedEvent.of(this));
    }

    @Override
    default void publishRenameRequest() {
        getApplicationEventPublisher().publishEvent(RenameRequestEvent.of(getDn()));
    }

    @Override
    default void publishChangePasswordRequest() {
        getApplicationEventPublisher().publishEvent(ChangePasswordRequestEvent.of(getDn()));
    }

    @Override
    default boolean hasFeature(@NonNull final Features feature) {
        return getUser().hasFeature(feature);
    }

    @Override
    default String getSortableType() {
        return "2:user";
    }
}
