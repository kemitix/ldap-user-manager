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

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.DirContextSource;

/**
 * Configuration for LDAP connections.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Configuration
class LdapConfiguration {

    /**
     * The LDAP template.
     *
     * @param contextSource the configuration for connecting to the directory server
     *
     * @return the LDAP Template
     */
    @Bean
    @Profile("default")
    LdapTemplate ldapTemplate(final ContextSource contextSource) {
        return new LdapTemplate(contextSource);
    }

    /**
     * The Configuration for connecting to the directory server.
     *
     * @param ldapOptions     The LDAP server options
     * @param ldapCredentials The LDAP Credentials
     *
     * @return the source for the directory context
     */
    @Bean
    @Profile("default")
    ContextSource contextSource(final LdapOptions ldapOptions, final LdapCredentials ldapCredentials) {
        final DirContextSource dirContextSource = new DirContextSource();
        dirContextSource.setUrls(ldapOptions.getUrls());
        dirContextSource.setBase(ldapOptions.getBase());
        dirContextSource.setUserDn(ldapOptions.getUserDn());
        dirContextSource.setPassword(ldapCredentials.getPassword());
        return dirContextSource;
    }
}
