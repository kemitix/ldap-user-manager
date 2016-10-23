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

import lombok.val;
import net.kemitix.ldapmanager.domain.OU;
import net.kemitix.ldapmanager.domain.User;
import net.kemitix.ldapmanager.state.LdapEntityContainer;
import net.kemitix.ldapmanager.state.LogMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.ldap.query.SearchScope;
import org.springframework.stereotype.Service;

import javax.naming.Name;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Default implementaion of the {@link LdapService}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Service
class DefaultLdapService implements LdapService {

    private final LdapTemplate ldapTemplate;

    private final LogMessages logMessages;

    /**
     * Constructor.
     *
     * @param ldapTemplate The LDAP Template.
     * @param logMessages  The Log Messages
     */
    @Autowired
    DefaultLdapService(final LdapTemplate ldapTemplate, final LogMessages logMessages) {
        this.ldapTemplate = ldapTemplate;
        this.logMessages = logMessages;
    }

    @Override
    public final LdapEntityContainer getLdapEntityContainer(final Name dn) {
        logMessages.add(String.format("Loading container: %s", dn));
        val query = LdapQueryBuilder.query()
                                    .base(dn)
                                    .searchScope(SearchScope.ONELEVEL)
                                    .where("objectclass")
                                    .isPresent();
        val ouList = ldapTemplate.find(query, OU.class);
        val userList = ldapTemplate.find(query, User.class);
        val ldapEntityContainer = LdapEntityContainer.of(Stream.of(ouList.stream(), userList.stream())
                                                               .flatMap(Function.identity()));
        logMessages.add(String.format("Loaded container: %d OU(s) and %d user(s)", ouList.size(), userList.size()));
        return ldapEntityContainer;
    }
}
