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

import lombok.extern.java.Log;
import net.kemitix.ldapmanager.domain.LdapEntity;
import net.kemitix.ldapmanager.domain.OU;
import net.kemitix.ldapmanager.domain.User;
import org.springframework.context.ApplicationEventPublisher;

import java.util.logging.Level;

/**
 * Factory for creating {@link NavigationItem} objects.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Log
@SuppressWarnings("hideutilityclassconstructor")
public final class NavigationItemFactory {

    /**
     * Create an OuNavigationItem.
     *
     * @param ou             The OU.
     * @param eventPublisher The Application Event Publisher.
     *
     * @return The ou navigation item.
     */
    public static NavigationItem create(final OU ou, final ApplicationEventPublisher eventPublisher) {
        log.log(Level.FINEST, "create<OU>(%s,...)", ou.name());
        return OuNavigationItem.create(ou, eventPublisher);
    }

    /**
     * Create a UserNavigationItem.
     *
     * @param user           The user.
     * @param eventPublisher The Application Event Publisher.
     *
     * @return The user navigation item.
     */
    public static NavigationItem create(final User user, final ApplicationEventPublisher eventPublisher) {
        log.log(Level.FINEST, "create<User>(%s,...)", user.name());
        return UserNavigationItem.create(user, eventPublisher);
    }

    /**
     * Create a NavigationItem.
     *
     * @param entity         The LDAP Entity.
     * @param eventPublisher The Application Event Publisher.
     *
     * @return The user navigation item.
     */
    public static NavigationItem create(final LdapEntity entity, final ApplicationEventPublisher eventPublisher) {
        // UGLY
        if (entity instanceof OU) {
            return create((OU) entity, eventPublisher);
        }
        return create((User) entity, eventPublisher);
    }
}
