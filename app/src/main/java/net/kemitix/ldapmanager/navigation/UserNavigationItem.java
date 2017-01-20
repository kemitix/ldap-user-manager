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
import lombok.NonNull;
import lombok.extern.java.Log;
import net.kemitix.ldapmanager.Messages;
import net.kemitix.ldapmanager.actions.rename.RenameRequestEvent;
import net.kemitix.ldapmanager.actions.user.password.ChangePasswordRequestEvent;
import net.kemitix.ldapmanager.domain.Features;
import net.kemitix.ldapmanager.domain.User;
import net.kemitix.ldapmanager.navigation.events.NavigationItemUserActionEvent;
import net.kemitix.ldapmanager.navigation.events.NavigationItemUserSelectedEvent;
import org.springframework.context.ApplicationEventPublisher;

import javax.naming.Name;
import java.util.Locale;
import java.util.logging.Level;

/**
 * Navigation Item for a User.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Log
public final class UserNavigationItem extends AbstractNavigationItem {

    @Getter
    private final User user;

    private final ApplicationEventPublisher applicationEventPublisher;

    private UserNavigationItem(final User user, final ApplicationEventPublisher eventPublisher) {
        super(user.getCn());
        this.user = user;
        applicationEventPublisher = eventPublisher;
    }

    /**
     * Create a UserNavigationItem.
     *
     * @param user           The user.
     * @param eventPublisher The Application Event Publisher.
     *
     * @return The user navigation item.
     */
    public static UserNavigationItem create(
            @NonNull final User user, @NonNull final ApplicationEventPublisher eventPublisher
                                           ) {
        log.log(Level.FINEST, "create(%s,...)", user.name());
        return new UserNavigationItem(user, eventPublisher);
    }

    @Override
    public void run() {
        log.log(Level.FINEST, "run(): %1", getName());
        applicationEventPublisher.publishEvent(NavigationItemUserActionEvent.of(user));
    }

    @Override
    public String toString() {
        return String.format(" %s ", user.name());
    }

    @Override
    public Name getDn() {
        return user.getDn();
    }

    @Override
    public void publishAsSelected() {
        log.log(Level.FINEST, "publishAsSelected(): %1", getName());
        applicationEventPublisher.publishEvent(NavigationItemUserSelectedEvent.of(this));
    }

    @Override
    public String getSortableName() {
        return String.format(
                "%s-%s", Messages.SORTABLE_PREFIX_USER.getValue(), getName().toLowerCase(Locale.getDefault()));
    }

    @Override
    public void publishRenameRequest() {
        log.log(Level.FINEST, "publishRenameRequest(): %1", getName());
        applicationEventPublisher.publishEvent(RenameRequestEvent.of(getDn()));
    }

    @Override
    public void publishChangePasswordRequest() {
        log.log(Level.FINEST, "publishChangePasswordRequest(): %1", getName());
        applicationEventPublisher.publishEvent(ChangePasswordRequestEvent.create(getDn()));
    }

    @Override
    public boolean hasFeature(final Features feature) {
        return user.hasFeature(feature);
    }
}
