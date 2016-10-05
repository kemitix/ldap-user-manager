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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.support.DirContextSource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * The LDAP Context Source.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Profile("!test")
@Component
class DefaultContextSource extends DirContextSource implements ContextSource {

    private final LdapOptions ldapOptions;

    private final LdapCredentials ldapCredentials;

    /**
     * Constructor.
     *
     * @param ldapOptions     The LDAP Options.
     * @param ldapCredentials The LDAP Credentials.
     */
    @Autowired
    DefaultContextSource(final LdapOptions ldapOptions, final LdapCredentials ldapCredentials) {
        this.ldapOptions = ldapOptions;
        this.ldapCredentials = ldapCredentials;
    }

    /**
     * Initializer.
     */
    @PostConstruct
    public void init() {
        setUrls(ldapOptions.getUrls());
        setBase(ldapOptions.getBase());
        setUserDn(ldapOptions.getUserDn());
        setPassword(ldapCredentials.getPassword());
    }
}
