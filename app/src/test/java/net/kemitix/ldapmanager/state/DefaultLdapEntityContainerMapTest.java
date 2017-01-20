package net.kemitix.ldapmanager.state;

import lombok.val;
import net.kemitix.ldapmanager.ldap.LdapNameUtil;
import net.kemitix.ldapmanager.ldap.LdapService;
import net.kemitix.ldapmanager.events.ContainerExpiredEvent;
import net.kemitix.ldapmanager.ldap.events.CurrentContainerChangedEvent;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

import javax.naming.Name;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;

/**
 * Tests for {@link DefaultLdapEntityContainerMap}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class DefaultLdapEntityContainerMapTest {

    private DefaultLdapEntityContainerMap containerMap;

    private Name dn;

    @Mock
    private LdapService ldapService;

    @Mock
    private LdapEntityContainer container;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @Mock
    private CurrentContainer currentContainer;

    @Captor
    private ArgumentCaptor<CurrentContainerChangedEvent> eventArgumentCaptor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        containerMap = new DefaultLdapEntityContainerMap(ldapService, applicationEventPublisher, currentContainer);
        dn = LdapNameUtil.parse("ou=users");
    }

    @Test
    public void getShouldQueryLdapServiceIfNotFound() {
        //given
        containerMap.clear();
        //when
        containerMap.get(dn);
        //then
        then(ldapService).should()
                         .getLdapEntityContainer(dn);
    }

    @Test
    public void getShouldReturnItemLdapServiceProvides() {
        //given
        given(ldapService.getLdapEntityContainer(dn)).willReturn(container);
        //when
        val result = containerMap.get(dn);
        //then
        assertThat(result).isSameAs(container);
    }

    @Test
    public void getShouldReturnSameItemOnSubsequentCall() throws Exception {
        //given
        given(ldapService.getLdapEntityContainer(dn)).willReturn(container);
        containerMap.get(dn);
        //when
        val result = containerMap.get(dn);
        //then
        assertThat(result).isSameAs(container);
    }

    @Test
    public void onContainerExpiredUpdateWhenCurrentContainerAffected() {
        //given
        val bobInUsers = LdapNameUtil.parse("cn=bob,ou=users");
        val ouUsers = LdapNameUtil.parse("ou=users");
        val event = ContainerExpiredEvent.containing(bobInUsers);
        given(currentContainer.getDn()).willReturn(ouUsers);
        //when
        containerMap.onContainerExpired(event);
        //then
        then(applicationEventPublisher).should()
                                       .publishEvent(eventArgumentCaptor.capture());
        assertThat(eventArgumentCaptor.getValue()
                                      .getNewContainer()).isEqualTo(ouUsers);
    }

    @Test
    public void onContainerExpiredDoNotUpdateWhenCurrentContainerNotAffected() {
        //given
        val bobInUsers = LdapNameUtil.parse("cn=bob,ou=users");
        val bobbyInManagers = LdapNameUtil.parse("cn=bobby,ou=managers");
        val ouSales = LdapNameUtil.parse("ou=sales");
        val event = ContainerExpiredEvent.containing(bobInUsers, bobbyInManagers);
        given(currentContainer.getDn()).willReturn(ouSales);
        //when
        containerMap.onContainerExpired(event);
        //then
        then(applicationEventPublisher).should(never())
                                       .publishEvent(any());
    }
}
