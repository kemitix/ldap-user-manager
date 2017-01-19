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

package net.kemitix.ldapmanager.navigation;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.val;
import net.kemitix.ldapmanager.domain.LdapEntity;
import net.kemitix.ldapmanager.domain.OU;
import net.kemitix.ldapmanager.ldap.LdapNameUtil;
import net.kemitix.ldapmanager.state.CurrentContainer;
import net.kemitix.ldapmanager.state.LdapEntityContainer;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.naming.Name;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.stream.Collectors;

/**
 * Supplier of navigation action items.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Log
@Component
@RequiredArgsConstructor
class NavigationItemsSupplier implements Supplier<List<NavigationItem>> {

    private static final String PARENT = "..";

    private final Supplier<LdapEntityContainer> currentLdapContainerSupplier;

    private final CurrentContainer currentContainer;

    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * An list of navigation items for the current container.
     *
     * @return the list of items
     */
    @Override
    public final List<NavigationItem> get() {
        log.log(Level.FINEST, "get()");
        val items = new ArrayList<NavigationItem>();
        // Add '..' entry to parent container
        LdapNameUtil.getParent(currentContainer.getDn())
                    .map(this::createParentNavigationItem)
                    .ifPresent(items::add);
        // Add entity in container
        items.addAll(currentLdapContainerSupplier.get()
                                                 .getContents()
                                                 .map(this::asNavigationItem)
                                                 .sorted()
                                                 .collect(Collectors.toList()));
        return items;
    }

    private NavigationItem asNavigationItem(final LdapEntity ldapEntity) {
        return ldapEntity.asNavigationItem(applicationEventPublisher);
    }

    private NavigationItem createParentNavigationItem(final Name parentDn) {
        return OU.createNonRenamable(parentDn, PARENT)
                 .asNavigationItem(applicationEventPublisher);
    }
}
