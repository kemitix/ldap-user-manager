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
import net.kemitix.ldapmanager.Messages;
import net.kemitix.ldapmanager.ldap.events.CurrentContainerChangedEvent;
import net.kemitix.ldapmanager.ldap.LdapNameUtil;
import net.kemitix.ldapmanager.ldap.LdapOptions;
import net.kemitix.ldapmanager.state.LogMessages;
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

    private final LogMessages logMessages;

    /**
     * Main constructor, creates a new Label displaying a specific text.
     *
     * @param ldapOptions       The LDAP Options
     * @param currentOuSupplier The supplier of the current OU
     * @param logMessages       The Log Messages
     */
    @Autowired
    CurrentOuLabel(
            final LdapOptions ldapOptions, final Supplier<String> currentOuSupplier, final LogMessages logMessages
                  ) {
        super(ldapOptions.getBase());
        this.baseDn = ldapOptions.getBase();
        this.currentOuSupplier = currentOuSupplier;
        this.logMessages = logMessages;
    }

    /**
     * Update the label when the current container changes.
     */
    @EventListener(CurrentContainerChangedEvent.class)
    public void onCurrentContainerChangerEventUpdateUiLabel() {
        final String dn = currentOuSupplier.get();
        logMessages.add(Messages.LOG_CHANGED_TO_CONTAINER + dn);
        setText(LdapNameUtil.join(dn, baseDn)
                            .toString());
    }
}
