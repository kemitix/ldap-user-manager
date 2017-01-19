package net.kemitix.ldapmanager.navigation.events;

import net.kemitix.ldapmanager.domain.User;
import net.kemitix.ldapmanager.navigation.UserNavigationItem;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.ldap.support.LdapNameBuilder;

import javax.naming.Name;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link NavigationItemUserSelectedEvent}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class NavigationItemUserSelectedEventTest {

    private NavigationItemUserSelectedEvent event;

    private UserNavigationItem userNavigationItem;

    private User user;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    private Name dn;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        dn = LdapNameBuilder.newInstance()
                            .build();
        user = User.builder()
                   .dn(dn)
                   .cn("bob")
                   .sn("smith")
                   .build();
        userNavigationItem = UserNavigationItem.create(user, applicationEventPublisher);
        event = NavigationItemUserSelectedEvent.of(userNavigationItem);
    }

    @Test
    public void getSelected() throws Exception {
        assertThat(event.getUserNavigationItem()).isSameAs(userNavigationItem);
    }

    @Test
    public void getDn() throws Exception {
        assertThat(event.getDn()).isSameAs(dn);
    }

    @Test
    public void shouldThrowNPEWhenCreateEventWithNullUserNavigationItem() {
        //given
        exception.expect(NullPointerException.class);
        exception.expectMessage("userNavigationItem");
        //when
        NavigationItemUserSelectedEvent.of(null);
    }
}
