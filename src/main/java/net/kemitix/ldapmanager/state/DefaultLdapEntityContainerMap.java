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

import net.kemitix.ldapmanager.ldap.LdapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.naming.Name;
import java.util.HashMap;
import java.util.Map;

/**
 * Map of LdapEntity containers by their DN name.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Component
class DefaultLdapEntityContainerMap implements LdapEntityContainerMap {

    private final LdapService ldapService;

    private final Map<Name, LdapEntityContainer> containerMap = new HashMap<>();

    /**
     * Constructor.
     *
     * @param ldapService The LDAP Service
     */
    @Autowired
    DefaultLdapEntityContainerMap(final LdapService ldapService) {
        this.ldapService = ldapService;
    }

    @Override
    public LdapEntityContainer get(final Name dn) {
        return containerMap.computeIfAbsent(dn, ldapService::getLdapEntityContainer);
    }

    @Override
    public void clear() {
        containerMap.clear();
    }
}
