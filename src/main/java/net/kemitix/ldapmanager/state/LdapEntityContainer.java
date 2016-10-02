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

package net.kemitix.ldapmanager.state;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import net.kemitix.ldapmanager.domain.LdapEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A container in an LDAP directory.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LdapEntityContainer {

    private static LdapEntityContainer empty = LdapEntityContainer.of(Collections.emptyList());

    private final List<LdapEntity> contents;

    /**
     * Creates an LDAP Container with the given contents.
     *
     * @param contents The contents of the container
     *
     * @return the container
     */
    public static LdapEntityContainer of(final List<LdapEntity> contents) {
        return new LdapEntityContainer(new ArrayList<>(contents));
    }

    /**
     * Returns an empty LDAP Container.
     *
     * @return the empty container
     */
    public static LdapEntityContainer empty() {
        return empty;
    }

    public final List<LdapEntity> getContents() {
        return new ArrayList<>(contents);
    }
}
