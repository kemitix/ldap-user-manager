package net.kemitix.ldapmanager.ui;

import com.googlecode.lanterna.gui2.dialogs.TextInputDialog;
import com.googlecode.lanterna.gui2.dialogs.TextInputDialogResultValidator;
import lombok.val;
import net.kemitix.ldapmanager.domain.User;
import net.kemitix.ldapmanager.ldap.LdapNameUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.naming.Name;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;

/**
 * Tests for {@link RenameDnDialog}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class DefaultRenameDnDialogTest {

    private RenameDnDialog dialog;

    @Mock
    private TextInputDialogFactory textInputDialogFactory;

    @Mock
    private TextInputDialogResultValidator validateNameNotEmpty;

    @Mock
    private TextInputDialog textInputDialog;

    @Captor
    private ArgumentCaptor<String> stringArgumentCaptor;

    private User user;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        dialog = new DefaultRenameDnDialog(textInputDialogFactory, validateNameNotEmpty);
        given(textInputDialogFactory.create(anyString(), any())).willReturn(textInputDialog);
        user = User.builder()
                   .dn(LdapNameUtil.parse("cn=bob,ou=users"))
                   .cn("bob")
                   .sn("smith")
                   .build();
    }

    @Test
    public void shouldPromptToChangeOnlyTheCn() {
        //given
        val dn = LdapNameUtil.parse("cn=bob,ou=users");
        given(textInputDialogFactory.getInput(any(TextInputDialog.class))).willReturn("bob");
        //when
        dialog.getRenamedDn(dn);
        //then
        then(textInputDialogFactory).should()
                                    .create(stringArgumentCaptor.capture(), eq(validateNameNotEmpty));
        assertThat(stringArgumentCaptor.getValue()).isEqualTo("bob");
    }

    @Test
    public void shouldGetRenamedUserDnWhenCnChanges() throws Exception {
        //given
        given(textInputDialogFactory.getInput(any(TextInputDialog.class))).willReturn("bobby");
        //when
        final Optional<Name> renamedUserDn = dialog.getRenamedDn(user.getDn());
        //then
        assertThat(renamedUserDn.toString()).contains("cn=bobby,ou=users");
    }

    @Test
    public void shouldGetEmptyWhenNotChanged() throws Exception {
        //when
        given(textInputDialogFactory.getInput(any(TextInputDialog.class))).willReturn("bob");
        //when
        final Optional<Name> renamedUserDn = dialog.getRenamedDn(user.getDn());
        //then
        assertThat(renamedUserDn).isEmpty();
    }

    @Test
    public void shouldDoNothingWhenCancelled() throws Exception {
        //when
        given(textInputDialogFactory.getInput(any(TextInputDialog.class))).willReturn(null);
        //when
        final Optional<Name> renamedUserDn = dialog.getRenamedDn(user.getDn());
        //then
        assertThat(renamedUserDn).isEmpty();
    }
}
