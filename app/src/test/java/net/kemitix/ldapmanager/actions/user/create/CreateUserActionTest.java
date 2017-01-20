package net.kemitix.ldapmanager.actions.user.create;

import lombok.val;
import net.kemitix.ldapmanager.domain.User;
import net.kemitix.ldapmanager.events.CreateUserCommitEvent;
import net.kemitix.ldapmanager.ldap.LdapNameUtil;
import net.kemitix.ldapmanager.events.ContainerExpiredEvent;
import net.kemitix.ldapmanager.ui.CloseCenterFormEvent;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.ldap.core.LdapTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

/**
 * Tests for {@link CreateUserAction}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class CreateUserActionTest {

    private CreateUserAction action;

    @Mock
    private LdapTemplate ldapTemplate;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Captor
    private ArgumentCaptor<Object> objectArgumentCaptor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        action = new CreateUserAction(ldapTemplate, applicationEventPublisher);
    }

    @Test
    public void shouldOnCreateUserCommit() throws Exception {
        //given
        val dn = LdapNameUtil.parse("ou=users");
        val cn = "bob";
        val sn = "smith";
        val event = CreateUserCommitEvent.of(dn, cn, sn);
        //when
        action.onCreateUserCommit(event);
        //then
        then(ldapTemplate).should()
                          .create(userArgumentCaptor.capture());
        val user = userArgumentCaptor.getValue();
        assertThat(user.getCn()).isEqualTo(cn);
        assertThat(user.getSn()).isEqualTo(sn);
        assertThat(user.getDn()
                       .getPrefix(1)).isEqualTo(dn);
        then(applicationEventPublisher).should(times(2))
                                       .publishEvent(objectArgumentCaptor.capture());
        val events = objectArgumentCaptor.getAllValues();
        assertThat(events.get(0)).isInstanceOf(CloseCenterFormEvent.class);
        assertThat(events.get(1)).isInstanceOf(ContainerExpiredEvent.class);
    }
}
