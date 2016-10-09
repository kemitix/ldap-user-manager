package net.kemitix.ldapmanager.state;

import lombok.val;
import net.kemitix.ldapmanager.domain.OU;
import net.kemitix.ldapmanager.events.CurrentContainerChangedEvent;
import net.kemitix.ldapmanager.events.NavigationItemSelectedEvent;
import net.kemitix.ldapmanager.ldap.LdapNameUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;

/**
 * Tests for {@link DefaultCurrentContainer}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class DefaultCurrentContainerTest {

    private DefaultCurrentContainer container;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        container = new DefaultCurrentContainer(eventPublisher);
        container.init();
    }

    @Test
    public void initShouldUpdateCurrentContainer() {
        then(eventPublisher).should()
                            .publishEvent(any(CurrentContainerChangedEvent.class));
    }

    @Test
    public void initShouldSetDnToEmpty() {
        // the initial DN is empty as it is equal to the base DN and is relative to it
        assertThat(container.getDn()
                            .isEmpty());
    }

    @Test
    public void shouldSetAndGetDn() throws Exception {
        //given
        val dn = LdapNameUtil.parse("ou=users");
        //when
        container.setDn(dn);
        //then
        assertThat(container.getDn()
                            .toString()).isEqualTo("ou=users");
    }

    @Test
    public void updateShouldPropagateIfOldNotEqualsNew() {
        //given
        val dnOld = LdapNameUtil.parse("ou=old");
        val dnNew = LdapNameUtil.parse("ou=new");
        container.setDn(dnOld);
        reset(eventPublisher);
        //when
        container.onNavigationItemSelectedOu(NavigationItemSelectedEvent.of(OU.builder()
                                                                              .dn(dnNew)
                                                                              .build()));
        //then
        ArgumentCaptor<CurrentContainerChangedEvent> eventArgumentCaptor =
                ArgumentCaptor.forClass(CurrentContainerChangedEvent.class);
        then(eventPublisher).should()
                            .publishEvent(eventArgumentCaptor.capture());
        assertThat(eventArgumentCaptor.getValue()
                                      .getSource()
                                      .toString()).isEqualTo(dnNew.toString());
    }

    @Test
    public void updateShouldNotPropagateIfOldEqualsNew() {
        //given
        val dn = LdapNameUtil.parse("ou=users");
        container.setDn(dn);
        reset(eventPublisher);
        //when
        container.onNavigationItemSelectedOu(NavigationItemSelectedEvent.of(OU.builder()
                                                                              .dn(dn)
                                                                              .build()));
        //then
        then(eventPublisher).should(never())
                            .publishEvent(any(CurrentContainerChangedEvent.class));
    }
}
