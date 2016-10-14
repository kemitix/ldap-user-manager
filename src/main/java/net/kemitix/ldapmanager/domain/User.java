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

package net.kemitix.ldapmanager.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import net.kemitix.ldapmanager.ldap.ObjectClass;
import net.kemitix.ldapmanager.navigation.NavigationItem;
import net.kemitix.ldapmanager.navigation.NavigationItemFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

import javax.naming.Name;
import java.util.logging.Level;

/**
 * A user.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Log
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Entry(objectClasses = {
        ObjectClass.INET_ORG_PERSON, ObjectClass.ORGANIZATIONAL_PERSON, ObjectClass.PERSON, ObjectClass.TOP
})
public final class User implements LdapEntity {

    @Id
    private Name dn;

    private String cn;

    private String sn;

    @Override
    public String name() {
        log.log(Level.FINEST, "name(): %1", cn);
        return cn;
    }

    @Override
    public NavigationItem asNavigationItem(final ApplicationEventPublisher eventPublisher) {
        return NavigationItemFactory.create(this, eventPublisher);
    }
}
