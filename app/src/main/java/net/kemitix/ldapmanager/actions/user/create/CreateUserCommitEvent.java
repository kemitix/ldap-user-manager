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

package net.kemitix.ldapmanager.actions.user.create;

import org.immutables.value.Value;

import javax.naming.Name;

/**
 * Raised when the user completes a Create User form.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Value.Immutable
interface CreateUserCommitEvent {

    /**
     * The DN of the container where the new user should be created.
     *
     * @return The DN of the container.
     */
    Name getDn();

    /**
     * The CN (common name) attribute of the new user.
     *
     * @return The CN attribute.
     */
    String getCn();

    /**
     * The SN (surname) attribute of the new user.
     *
     * @return The SN attribute.
     */
    String getSn();

    /**
     * Create a new CreateUserCommitEvent.
     *
     * @param dn The DN of the container where the new user should be created.
     * @param cn The CN (common name) attribute of the new user.
     * @param sn The SN (surname) attribute of the new user.
     *
     * @return the event.
     */
    static CreateUserCommitEvent create(final Name dn, final String cn, final String sn) {
        return ImmutableCreateUserCommitEvent.builder()
                                             .dn(dn)
                                             .cn(cn)
                                             .sn(sn)
                                             .build();
    }
}
