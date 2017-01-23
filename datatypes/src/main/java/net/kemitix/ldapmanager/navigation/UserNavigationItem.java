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

import net.kemitix.ldapmanager.domain.Features;
import net.kemitix.ldapmanager.domain.User;
import net.kemitix.ldapmanager.events.ChangePasswordRequestEvent;
import net.kemitix.ldapmanager.events.RenameRequestEvent;
import org.immutables.value.Value;
import org.springframework.context.ApplicationEventPublisher;

/**
 * Navigation Item for a User.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Value.Immutable
public interface UserNavigationItem extends NamedNavigationItem {

    User getUser();

    ApplicationEventPublisher getApplicationEventPublisher();

    /**
     * Create the builder.
     *
     * @return The builder
     */
    static ImmutableUserNavigationItem.Builder builder() {
        return ImmutableUserNavigationItem.builder();
    }

    static UserNavigationItem create(User user, ApplicationEventPublisher eventPublisher) {
        return builder().user(user)
                        .applicationEventPublisher(eventPublisher)
                        .build();
    }

    //    /**
    //     * Create a UserNavigationItem.
    //     *
    //     * @param user           The user.
    //     * @param eventPublisher The Application Event Publisher.
    //     *
    //     * @return The user navigation item.
    //     */
    //    public static UserNavigationItem create(
    //            @NonNull final User user, @NonNull final ApplicationEventPublisher eventPublisher
    //                                           ) {
    //        log.log(Level.FINEST, "create(%s,...)", user.name());
    //        return new UserNavigationItem(user, eventPublisher);
    //    }
    //
    @Override
    default void run() {
        //        getApplicationEventPublisher().publishEvent(NavigationItemUserActionEvent.of(user));
    }

    //
    //    @Override
    //    public String toString() {
    //        return String.format(" %s ", user.name());
    //    }
    //
    //    @Override
    //    public Name getDn() {
    //        return user.getDn();
    //    }
    //
    //    @Override
    default void publishAsSelected() {
        //        log.log(Level.FINEST, "publishAsSelected(): %1", getName());
        //            getApplicationEventPublisher().publishEvent(NavigationItemUserSelectedEvent.of(this));
    }

    //
    //    @Override
    //    public String getSortableName() {
    //        return String.format(
    //                "%s-%s", Messages.SORTABLE_PREFIX_USER.getValue(), getName().toLowerCase(Locale.getDefault()));
    //    }
    //
    //    @Override
    default void publishRenameRequest() {
        //        log.log(Level.FINEST, "publishRenameRequest(): %1", getName());
        getApplicationEventPublisher().publishEvent(RenameRequestEvent.of(getDn()));
    }

    //
    //    @Override
    default void publishChangePasswordRequest() {
        //        log.log(Level.FINEST, "publishChangePasswordRequest(): %1", getName());
        getApplicationEventPublisher().publishEvent(ChangePasswordRequestEvent.of(getDn()));
    }

    //
    //    @Override
    default boolean hasFeature(final Features feature) {
        return getUser().hasFeature(feature);
    }
}
