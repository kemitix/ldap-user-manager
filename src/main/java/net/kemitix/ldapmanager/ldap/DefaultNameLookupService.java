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
import net.kemitix.ldapmanager.domain.LdapEntity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;

import javax.naming.Name;
import javax.naming.NamingException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Default Implementation of Name Lookup Service.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Service
class DefaultNameLookupService implements NameLookupService {

    private final LdapTemplate ldapTemplate;

    private final Set<PreCheckContextMapper<? extends LdapEntity>> contextMappers;

    /**
     * Constructor.
     *
     * @param ldapTemplate   The LDAP Template.
     * @param contextMappers The Context Mappers for LdapEntity objects.
     */
    DefaultNameLookupService(
            final LdapTemplate ldapTemplate, final Set<PreCheckContextMapper<? extends LdapEntity>> contextMappers
                            ) {
        this.ldapTemplate = ldapTemplate;
        this.contextMappers = new HashSet<>(contextMappers);
    }

    @Cacheable
    @Override
    public Optional<LdapEntity> findByDn(final Name dn) {
        val context = ldapTemplate.lookupContext(dn);
        return contextMappers.stream()
                             .filter(mapper -> mapper.canMapContext(context))
                             .findFirst()
                             .flatMap(mapper -> {
                                 try {
                                     return Optional.of(mapper.mapFromContext(context));
                                 } catch (NamingException e) {
                                     return Optional.empty();
                                 }
                             });
    }
}
