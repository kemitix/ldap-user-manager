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

package net.kemitix.ldapmanager.ldap;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.ldap.support.LdapNameBuilder;

import javax.naming.InvalidNameException;
import javax.naming.Name;
import java.util.Optional;

/**
 * Utility class for working with {@link Name} objects.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@SuppressWarnings("hideutilityclassconstructor")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class LdapNameUtil {

    private static final Name EMPTY = LdapNameBuilder.newInstance()
                                                     .build();

    /**
     * Returns the parent of the supplied name.
     *
     * @param name the child name
     *
     * @return the parent name in an Optional, or empty if name is empty
     */
    public static Optional<Name> getParent(final Name name) {
        if (name.isEmpty()) {
            return Optional.empty();
        }
        Name parent = LdapNameBuilder.newInstance(name)
                                     .build();
        try {
            parent.remove(parent.size() - 1);
        } catch (InvalidNameException e) {
            parent = null;
        }
        return Optional.ofNullable(parent);
    }

    /**
     * Joins the local name to the base name to create a complete name.
     *
     * @param localName The local part of the name.
     * @param baseName  The base part of the name
     *
     * @return the completed name
     */
    public static Name join(final Name localName, final Name baseName) {
        return LdapNameBuilder.newInstance(baseName)
                              .add(localName)
                              .build();
    }

    /**
     * Joins the local name to the base name to create a complete name.
     *
     * @param localName The local part of the name.
     * @param baseName  The base part of the name
     *
     * @return the completed name
     */
    public static Name join(final String localName, final String baseName) {
        return join(parse(localName), parse(baseName));
    }

    /**
     * Converts the name into a Name.
     *
     * @param name The name to parse
     *
     * @return the parsed name object
     */
    public static Name parse(final String name) {
        return LdapNameBuilder.newInstance(name)
                              .build();
    }

    /**
     * Returns the empty Name.
     *
     * @return the empty name
     */
    public static Name empty() {
        return EMPTY;
    }

    /**
     * Returns a new Name with the identifying attribute replaced.
     *
     * @param original  The original Name
     * @param attribute The identifying attribute (e.g. 'cn' or 'ou')
     * @param value     The new attribute value
     *
     * @return the new Name
     */
    public static Name replaceIdAttribute(final Name original, final String attribute, final String value) {
        return getParent(original).map(LdapNameBuilder::newInstance)
                                  .orElseGet(LdapNameBuilder::newInstance)
                                  .add(attribute, value)
                                  .build();
    }
}
