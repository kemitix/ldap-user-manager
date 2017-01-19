package net.kemitix.ldapmanager.ldap;

import net.kemitix.ldapmanager.domain.LdapEntity;
import net.kemitix.ldapmanager.domain.OU;
import net.kemitix.ldapmanager.domain.User;
import net.kemitix.ldapmanager.ldap.events.ContainerExpiredEvent;
import net.kemitix.ldapmanager.state.LdapEntityContainer;
import net.kemitix.ldapmanager.state.LogMessages;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ldap.core.LdapTemplate;

import javax.naming.Name;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;

/**
 * Tests for {@link LdapService}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class DefaultLdapServiceTest {

    private LdapService ldapService;

    @Mock
    private LdapTemplate ldapTemplate;

    @Mock
    private LogMessages logMessages;

    private Name ouDn;

    private Name userDn;

    private OU ou;

    private User user;


    @Mock
    private LdapEntity ldapEntity;

    @Captor
    private ArgumentCaptor<ContainerExpiredEvent> eventArgumentCaptor;

    private OUEntity ouEntity;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ldapService = new DefaultLdapService(ldapTemplate, logMessages);
        ouDn = LdapNameUtil.empty();
        userDn = LdapNameUtil.empty();
        ouEntity = new OUEntity();
        ouEntity.setDn(ouDn);
        ouEntity.setOu("ou");
    }

    @Test
    public void shouldGetLdapEntityContainer() throws Exception {
        //given
        user = User.builder()
                   .dn(userDn)
                   .build();
        given(ldapTemplate.find(anyObject(), eq(OUEntity.class))).willReturn(Collections.singletonList(ouEntity));
        given(ldapTemplate.find(anyObject(), eq(User.class))).willReturn(Collections.singletonList(user));
        //when
        final LdapEntityContainer result = ldapService.getLdapEntityContainer(ouDn);
        //then
        assertThat(result.getContents()).extracting("dn")
                                        .containsExactlyInAnyOrder(ouDn, userDn);
    }
}
