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

import lombok.Setter;
import lombok.ToString;
import net.kemitix.ldapmanager.domain.User;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

import javax.naming.Name;

/**
 * LDAP Entity mapper for {@link User}.
 *
 * @author Paul Campbell (paul.campbell@hubio.com)
 */
@ToString
@Setter
@Entry(objectClasses = {
        ObjectClass.INET_ORG_PERSON, ObjectClass.ORGANIZATIONAL_PERSON, ObjectClass.PERSON, ObjectClass.TOP
})
public class UserEntity {

    @Id
    private Name dn;

    private String cn;

    private String sn;

    /**
     * Convert the UserEntity into an immutable User.
     *
     * @return The immutable User.
     */
    final User fromLdap() {
        return User.of(dn, cn, sn);
    }

    /**
     * Convert a User into a UserEntity.
     *
     * @param user The user.
     *
     * @return The Entity.
     */
    static UserEntity from(final User user) {
        final UserEntity userEntity = new UserEntity();
        userEntity.setDn(user.getDn());
        userEntity.setCn(user.getCn());
        userEntity.setSn(user.getSn());
        return userEntity;
    }
}
