package net.kemitix.ldapmanager.actions.user.rename;

import com.googlecode.lanterna.gui2.dialogs.TextInputDialog;
import com.googlecode.lanterna.gui2.dialogs.TextInputDialogResultValidator;
import lombok.val;
import net.kemitix.ldapmanager.domain.User;
import net.kemitix.ldapmanager.ldap.LdapNameUtil;
import net.kemitix.ldapmanager.ui.TextInputDialogFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.naming.Name;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

/**
 * Tests for {@link DefaultRenameUserDialog}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class DefaultRenameUserDialogTest {

    private DefaultRenameUserDialog dialog;

    @Mock
    private TextInputDialogFactory textInputDialogFactory;

    @Mock
    private TextInputDialogResultValidator validateNameNotEmpty;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        dialog = new DefaultRenameUserDialog(textInputDialogFactory, validateNameNotEmpty);
    }

    @Test
    public void shouldGetRenamedUserDnWhenCnChanges() throws Exception {
        //given
        val user = User.builder()
                       .dn(LdapNameUtil.parse("cn=bob,ou=users"))
                       .cn("bob")
                       .build();
        given(textInputDialogFactory.getInput(any(TextInputDialog.class))).willReturn("bobby");
        //when
        final Optional<Name> renamedUserDn = dialog.getRenamedUserDn(user);
        //then
        assertThat(renamedUserDn.toString()).contains("cn=bobby,ou=users");
    }

    @Test
    public void shouldGetRenamedUserDnWhenCnNotChanged() throws Exception {
        val user = User.builder()
                       .dn(LdapNameUtil.parse("cn=bob,ou=users"))
                       .cn("bob")
                       .build();
        given(textInputDialogFactory.getInput(any(TextInputDialog.class))).willReturn("bob");
        //when
        final Optional<Name> renamedUserDn = dialog.getRenamedUserDn(user);
        //then
        assertThat(renamedUserDn).isEmpty();
    }
}
