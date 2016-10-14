package net.kemitix.ldapmanager.navigation.events;

import lombok.val;
import net.kemitix.ldapmanager.domain.OU;
import net.kemitix.ldapmanager.navigation.OuNavigationItem;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.ldap.support.LdapNameBuilder;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link NavigationItemOuSelectedEvent}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class NavigationItemOuSelectedEventTest {

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldCreateEventAndGetSelectionAndDnBackOut() throws Exception {
        //given
        val dn = LdapNameBuilder.newInstance()
                                .build();
        val ou = OU.builder()
                   .dn(dn)
                   .build();
        val ouNavigationItem = OuNavigationItem.create(ou, applicationEventPublisher);
        //when
        val event = NavigationItemOuSelectedEvent.of(ouNavigationItem);
        //then
        assertThat(event.getOuNavigationItem()).isSameAs(ouNavigationItem);
        assertThat(event.getDn()).isSameAs(dn);
    }

    @Test
    public void shouldThrowNPEWhenCreateEventWithNullOu() {
        //given
        exception.expect(NullPointerException.class);
        exception.expectMessage("ouNavigationItem");
        //when
        NavigationItemOuSelectedEvent.of(null);
    }
}
