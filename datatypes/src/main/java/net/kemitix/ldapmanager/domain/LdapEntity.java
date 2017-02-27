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

import org.immutables.value.Value;

import javax.naming.Name;
import java.util.Set;

/**
 * Common interface for LDAP Entities.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public interface LdapEntity extends FeatureSet {

    /**
     * Returns the DN attribute.
     *
     * @return the DN
     */
    Name getDn();

    /**
     * Returns the set of features supported by the object.
     *
     * @return The Set of Features.
     */
    Set<Features> getFeatureSet();

    /**
     * Returns the name of an entity.
     *
     * <p>For Users this would the 'cn', for OUs this would the 'ou' value.</p>
     *
     * @return the name of the entity
     */
    @Value.Derived
    String name();

    /**
     * Checks if the entity supports the feature.
     *
     * @param feature The Feature to check for.
     *
     * @return True if the feature is supported.
     */
    default boolean hasFeature(final Features feature) {
        return getFeatureSet().contains(feature);
    }
}
