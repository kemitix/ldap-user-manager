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

package net.kemitix.ldapmanager.ui;

import com.googlecode.lanterna.gui2.Label;
import net.kemitix.ldapmanager.events.CurrentContainerChangedEvent;
import net.kemitix.ldapmanager.ldap.LdapOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

/**
 * The current OU Label.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Component
class CurrentOuLabel extends Label {

    private final String baseDn;

    private final Supplier<String> currentOuSupplier;

    /**
     * Main constructor, creates a new Label displaying a specific text.
     *
     * @param ldapOptions       The LDAP Options
     * @param currentOuSupplier The supplier of the current OU
     */
    @Autowired
    CurrentOuLabel(final LdapOptions ldapOptions, final Supplier<String> currentOuSupplier) {
        super(ldapOptions.getBase());
        this.baseDn = ldapOptions.getBase();
        this.currentOuSupplier = currentOuSupplier;
    }

    /**
     * Update the label when the current container changes.
     */
    @EventListener(CurrentContainerChangedEvent.class)
    public void onCurrentContainerChangerEventUpdateUiLabel() {
        final String dn = currentOuSupplier.get();
        if (dn.isEmpty()) {
            setText(baseDn);
        } else {
            setText(String.join(",", dn, baseDn));
        }
    }
}
