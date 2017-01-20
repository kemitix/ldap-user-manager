package net.kemitix.ldapmanager.actions.user.password;

import net.kemitix.ldapmanager.ldap.LdapNameUtil;
import net.kemitix.ldapmanager.state.LogMessages;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.security.authentication.encoding.PasswordEncoder;

import javax.naming.Name;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Tests for {@link ChangePasswordAction}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class ChangePasswordActionTest {

    private ChangePasswordAction action;

    @Mock
    private PasswordDialog passwordDialog;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private LdapTemplate ldapTemplate;

    @Mock
    private LogMessages logMessages;

    private ChangePasswordRequestEvent event;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @Mock
    private DirContextOperations context;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        action = new ChangePasswordAction(passwordDialog, passwordEncoder, ldapTemplate, logMessages);
    }

    @Test
    public void whenUserCancelsFirstDialogShouldNotDisplaySecond() throws Exception {
        //given
        given(passwordDialog.getPassword(anyString())).willReturn(Optional.empty());
        event = ChangePasswordRequestEvent.of(LdapNameUtil.empty());
        //when
        action.onChangeUserPasswordRequest(event);
        //then
        then(passwordDialog).should(times(1))
                            .getPassword(anyString());
    }

    @Test
    public void whenUserCancelsSecondDialogShouldNotUseLdapTemplate() throws Exception {
        //given
        given(passwordDialog.getPassword(anyString())).willReturn(Optional.of("password"))
                                                      .willReturn(Optional.empty());
        event = ChangePasswordRequestEvent.of(LdapNameUtil.empty());
        //when
        action.onChangeUserPasswordRequest(event);
        //then
        then(passwordDialog).should(times(2))
                            .getPassword(anyString());
        verifyNoMoreInteractions(ldapTemplate);
    }

    @Test
    public void whenUserEntersDifferentPasswordShouldNotUseLdapTemplate() throws Exception {
        //given
        given(passwordDialog.getPassword(anyString())).willReturn(Optional.of("password"))
                                                      .willReturn(Optional.of("different"));
        event = ChangePasswordRequestEvent.of(LdapNameUtil.empty());
        //when
        action.onChangeUserPasswordRequest(event);
        //then
        then(passwordDialog).should(times(2))
                            .getPassword(anyString());
        verifyNoMoreInteractions(ldapTemplate);
    }

    @Test
    public void whenUserEntersValidPasswordsShouldSetPassword() throws Exception {
        //given
        given(passwordDialog.getPassword(anyString())).willReturn(Optional.of("password"))
                                                      .willReturn(Optional.of("password"));
        event = ChangePasswordRequestEvent.of(LdapNameUtil.empty());
        given(passwordEncoder.encodePassword(anyString(), eq(null))).willReturn("encoded");
        given(ldapTemplate.lookupContext(any(Name.class))).willReturn(context);
        //when
        action.onChangeUserPasswordRequest(event);
        //then
        then(passwordDialog).should(times(2))
                            .getPassword(anyString());
        then(ldapTemplate).should()
                          .modifyAttributes(context);
    }
}
