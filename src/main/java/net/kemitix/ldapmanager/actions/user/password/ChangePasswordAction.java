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

package net.kemitix.ldapmanager.actions.user.password;

import lombok.extern.java.Log;
import lombok.val;
import net.kemitix.ldapmanager.state.LogMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.naming.Name;
import java.util.Optional;
import java.util.logging.Level;

/**
 * Action to handle changing a User password.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Log
@Component
class ChangePasswordAction {

    private static final String PROMPT1 = "Enter the new password";

    private static final String PROMPT2 = "Confirm new password";

    private final PasswordDialog passwordDialog;

    private final PasswordEncoder passwordEncoder;

    private final LdapTemplate ldapTemplate;

    private final LogMessages logMessages;

    /**
     * Constructor.
     *
     * @param passwordDialog  The Dialog to display to get the new password.
     * @param passwordEncoder The password encoder.
     * @param ldapTemplate    The LDAP Template
     * @param logMessages     The Log Messages.
     */
    @Autowired
    ChangePasswordAction(
            final PasswordDialog passwordDialog, final PasswordEncoder passwordEncoder, final LdapTemplate ldapTemplate,
            final LogMessages logMessages
                        ) {
        this.passwordDialog = passwordDialog;
        this.passwordEncoder = passwordEncoder;
        this.ldapTemplate = ldapTemplate;
        this.logMessages = logMessages;
    }

    /**
     * Listener for {@link ChangePasswordRequestEvent} to prompt the user for the new password and to then update the
     * LDAP server.
     *
     * @param event the event
     */
    @EventListener(ChangePasswordRequestEvent.class)
    public final void onChangeUserPasswordRequest(final ChangePasswordRequestEvent event) {
        val dn = event.getUserNavigationItem()
                      .getUser()
                      .getDn();
        log.log(Level.FINEST, "onChangeUserPasswordRequest(%s)", dn);
        getPassword(PROMPT1).ifPresent(pass -> getPassword(PROMPT2).filter(pass::equals)
                                                                   .map(this::encodePassword)
                                                                   .ifPresent(encrypted -> setPassword(dn, encrypted)));
    }

    private String encodePassword(final String password) {
        return passwordEncoder.encodePassword(password, null);
    }

    private Optional<String> getPassword(final String description) {
        return passwordDialog.getPassword(description);
    }

    private void setPassword(final Name dn, final String password) {
        logMessages.add(String.format("Setting %s password to %s", dn, password));
        val context = ldapTemplate.lookupContext(dn);
        context.setAttributeValue("userPassword", password);
        ldapTemplate.modifyAttributes(context);
    }
}
