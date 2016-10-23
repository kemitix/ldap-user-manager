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
import net.kemitix.ldapmanager.domain.User;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.support.AbstractContextMapper;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

/**
 * Context Mapper for Users.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Service
class UserContextMapper extends AbstractContextMapper<User> implements PreCheckContextMapper<User> {

    @Override
    protected User doMapFromContext(final DirContextOperations ctx) {
        return User.builder()
                   .dn(ctx.getDn())
                   .cn(ctx.getStringAttribute(LdapAttribute.CN))
                   .sn(ctx.getStringAttribute(LdapAttribute.SN))
                   .build();
    }

    @Override
    public boolean canMapContext(final DirContextOperations ctx) {
        val attributes = ctx.getAttributes();
        return Stream.of("cn", "sn")
                     .allMatch(attribute -> attributes.get(attribute) != null);
    }
}
