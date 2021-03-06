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

import net.kemitix.ldapmanager.navigation.NavigationItem;
import org.springframework.context.ApplicationEventPublisher;

import javax.naming.Name;

/**
 * Common interface for LDAP Entities.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public interface LdapEntity extends FeatureSet {

    /**
     * Returns the name of an entity.
     *
     * <p>For Users this would the 'cn', for OUs this would the 'ou' value.</p>
     *
     * @return the name of the entity
     */
    String name();

    /**
     * Create a NavigationItem for this.
     *
     * @param eventPublisher The Application Event Publisher
     *
     * @return the navigation item
     */
    NavigationItem asNavigationItem(ApplicationEventPublisher eventPublisher);

    /**
     * Returns the DN attribute.
     *
     * @return the DN
     */
    Name getDn();
}
