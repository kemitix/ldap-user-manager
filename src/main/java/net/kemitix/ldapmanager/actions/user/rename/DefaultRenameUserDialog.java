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

package net.kemitix.ldapmanager.actions.user.rename;

import com.googlecode.lanterna.gui2.dialogs.TextInputDialog;
import com.googlecode.lanterna.gui2.dialogs.TextInputDialogResultValidator;
import lombok.val;
import net.kemitix.ldapmanager.Messages;
import net.kemitix.ldapmanager.domain.User;
import net.kemitix.ldapmanager.ldap.LdapNameUtil;
import net.kemitix.ldapmanager.ui.TextInputDialogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.naming.Name;
import java.util.Optional;

/**
 * Implementation of dialog for prompting for a new {@link User} cn attribute value.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Component
class DefaultRenameUserDialog implements RenameUserDialog {

    private final TextInputDialogFactory textInputDialogFactory;

    private final TextInputDialogResultValidator validateNameNotEmpty;

    /**
     * Constructor.
     *
     * @param textInputDialogFactory The Factory for creating and managing text input dialogs.
     * @param validateNameNotEmpty   The Validator to ensure name is not empty.
     */
    @Autowired
    DefaultRenameUserDialog(
            final TextInputDialogFactory textInputDialogFactory,
            final TextInputDialogResultValidator validateNameNotEmpty
                           ) {
        this.textInputDialogFactory = textInputDialogFactory;
        this.validateNameNotEmpty = validateNameNotEmpty;
    }

    @Override
    public final Optional<Name> getRenamedUserDn(final User user) {
        final TextInputDialog dialog = textInputDialogFactory.create(user.getCn(), validateNameNotEmpty);
        dialog.setTitle(String.format(Messages.PROMPT_RENAME_USER.getValue(), user.getCn()));
        val cn = textInputDialogFactory.getInput(dialog);
        if ((cn == null) || cn.equals(user.getCn())) {
            return Optional.empty();
        }
        return Optional.of(LdapNameUtil.replaceCn(user.getDn(), cn));
    }
}
