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

import com.googlecode.lanterna.gui2.dialogs.TextInputDialog;
import com.googlecode.lanterna.gui2.dialogs.TextInputDialogResultValidator;
import lombok.RequiredArgsConstructor;
import lombok.val;
import net.kemitix.ldapmanager.Messages;
import net.kemitix.ldapmanager.ldap.LdapNameUtil;
import org.springframework.stereotype.Component;

import javax.naming.Name;
import java.util.Optional;

/**
 * Implementation of dialog for prompting for a new name.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Component
@RequiredArgsConstructor
class DefaultRenameDnDialog implements RenameDnDialog {

    private final TextInputDialogFactory textInputDialogFactory;

    private final TextInputDialogResultValidator validateNameNotEmpty;

    /**
     * Prompt the used with a dialog to provide the new DN.
     *
     * <p>If the user cancels the dialog, or the value is left unchanged, then an empty optional will be returned.</p>
     *
     * @param name The user to be renamed
     *
     * @return an optional containing the new name, or empty if dialog cancelled or the name was unchanged.
     */
    public final Optional<Name> getRenamedDn(final Name name) {
        val rdn = name.get(name.size() - 1)
                      .split("=");
        val attribute = rdn[0];
        val oldName = rdn[1];
        final TextInputDialog dialog = textInputDialogFactory.create(oldName, validateNameNotEmpty);
        dialog.setTitle(String.format(Messages.PROMPT_RENAME_USER.getValue(), name));
        val cn = textInputDialogFactory.getInput(dialog);
        if ((cn == null) || cn.equals(oldName)) {
            return Optional.empty();
        }
        return Optional.of(LdapNameUtil.replaceIdAttribute(name, attribute, cn));
    }
}
