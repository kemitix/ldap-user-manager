package net.kemitix.ldapmanager.ldap;

import lombok.val;
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
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.ldap.core.LdapTemplate;

import javax.naming.Name;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
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

    private Name dn;

    private OU ou;

    private User user;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @Mock
    private LdapEntity ldapEntity;

    @Captor
    private ArgumentCaptor<ContainerExpiredEvent> eventArgumentCaptor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ldapService = new DefaultLdapService(ldapTemplate, logMessages, applicationEventPublisher);
    }

    @Test
    public void shouldGetLdapEntityContainer() throws Exception {
        //given
        dn = LdapNameUtil.empty();
        ou = OU.builder()
               .build();
        user = User.builder()
                   .build();
        given(ldapTemplate.find(anyObject(), eq(OU.class))).willReturn(Collections.singletonList(ou));
        given(ldapTemplate.find(anyObject(), eq(User.class))).willReturn(Collections.singletonList(user));
        //when
        final LdapEntityContainer result = ldapService.getLdapEntityContainer(dn);
        //then
        assertThat(result.getContents()).containsExactlyInAnyOrder(ou, user);
    }

    @Test
    public void shouldExpireOneContainerWhenRenamingLdapEntity() throws Exception {
        //given
        val oldDn = LdapNameUtil.parse("cn=bob,ou=users");
        dn = LdapNameUtil.parse("cn=bobby,ou=users");
        given(ldapEntity.getDn()).willReturn(oldDn);
        //when
        ldapService.rename(ldapEntity, dn);
        //then
        then(ldapTemplate).should()
                          .rename(oldDn, dn);
        then(applicationEventPublisher).should()
                                       .publishEvent(eventArgumentCaptor.capture());
        assertThat(eventArgumentCaptor.getValue()
                                      .getContainers()).containsOnly(LdapNameUtil.parse("ou=users"));
    }

    @Test
    public void shouldExpireTwoContainerWhenMovingLdapEntity() throws Exception {
        //given
        val oldDn = LdapNameUtil.parse("cn=bob,ou=users");
        dn = LdapNameUtil.parse("cn=bobby,ou=managers");
        given(ldapEntity.getDn()).willReturn(oldDn);
        //when
        ldapService.rename(ldapEntity, dn);
        //then
        then(ldapTemplate).should()
                          .rename(oldDn, dn);
        then(applicationEventPublisher).should()
                                       .publishEvent(eventArgumentCaptor.capture());
        assertThat(eventArgumentCaptor.getValue()
                                      .getContainers()).containsOnly(LdapNameUtil.parse("ou=users"),
                                                                     LdapNameUtil.parse("ou=managers"));
    }
}
