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

package net.kemitix.ldapmanager.suppliers;

import net.kemitix.ldapmanager.domain.LdapEntity;
import net.kemitix.ldapmanager.events.NavigationItemSelectedEvent;
import net.kemitix.ldapmanager.state.LdapEntityContainer;
import net.kemitix.ldapmanager.util.nameditem.NamedItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Supplier of navigation action items.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Component
class NavigationItemsSupplier implements Supplier<List<NamedItem<Runnable>>> {

    private final Supplier<LdapEntityContainer> currentLdapContainerSupplier;

    private final ApplicationEventPublisher eventPublisher;

    /**
     * Constructor.
     *
     * @param currentLdapContainerSupplier The supplier of the node for the current LdapEntityContainer.
     * @param eventPublisher               The Application Event Publisher.
     */
    @Autowired
    NavigationItemsSupplier(
            final Supplier<LdapEntityContainer> currentLdapContainerSupplier,
            final ApplicationEventPublisher eventPublisher
                           ) {
        this.currentLdapContainerSupplier = currentLdapContainerSupplier;
        this.eventPublisher = eventPublisher;
    }

    /**
     * An list of navigation items for the current container.
     *
     * @return the list of items
     */
    @Override
    public List<NamedItem<Runnable>> get() {
        return new ArrayList<>(currentLdapContainerSupplier.get()
                                                           .getContents()
                                                           .stream()
                                                           .map(this::getNamedItem)
                                                           .collect(Collectors.toList()));
    }

    private NamedItem<Runnable> getNamedItem(final LdapEntity ldapEntity) {
        return NamedItem.of(ldapEntity.name(), () -> doAction(ldapEntity));
    }

    private void doAction(final LdapEntity ldapEntity) {
        eventPublisher.publishEvent(NavigationItemSelectedEvent.of(ldapEntity));
    }
}
