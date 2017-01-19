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

import lombok.RequiredArgsConstructor;
import net.kemitix.ldapmanager.ldap.events.CurrentContainerChangedEvent;
import net.kemitix.ldapmanager.state.CurrentContainer;
import net.kemitix.ldapmanager.state.LdapEntityContainer;
import net.kemitix.ldapmanager.state.LdapEntityContainerMap;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

/**
 * Supplier for the {@link LdapEntityContainer} for the current container.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Component
@RequiredArgsConstructor
class CurrentLdapEntityContainerSupplier implements Supplier<LdapEntityContainer> {

    private final CurrentContainer currentContainer;

    private final LdapEntityContainerMap containerMap;

    @Override
    public LdapEntityContainer get() {
        return containerMap.get(currentContainer.getDn());
    }

    /**
     * Load the contents of the container from the LDAP server.
     */
    @EventListener(CurrentContainerChangedEvent.class)
    @Order(1)
    public void onCurrentContainerChangedEventLoadLdapContainer() {
        get();
    }
}
