package net.kemitix.ldapmanager.navigation.events;

import lombok.val;
import net.kemitix.ldapmanager.domain.OU;
import net.kemitix.ldapmanager.domain.User;
import net.kemitix.ldapmanager.events.NavigationItemOuSelectedEvent;
import net.kemitix.ldapmanager.events.NavigationItemUserSelectedEvent;
import net.kemitix.ldapmanager.ldap.LdapNameUtil;
import net.kemitix.ldapmanager.navigation.NavigationItem;
import net.kemitix.ldapmanager.navigation.OuNavigationItem;
import net.kemitix.ldapmanager.navigation.UserNavigationItem;
import net.kemitix.ldapmanager.state.LogMessages;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

import static org.mockito.BDDMockito.then;

/**
 * Tests for {@link SelectionChangeListener}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class SelectionChangeListenerTest {

    private SelectionChangeListener listener;

    @Mock
    private LogMessages logMessages;

    @Mock
    private NavigationItem oldItem;

    @Mock
    private NavigationItem newItem;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        listener = new SelectionChangeListener(logMessages);
    }

    @Test
    public void shouldOnSelectionChanged() throws Exception {
        //given
        val event = NavigationItemSelectionChangedEvent.of(oldItem, newItem);
        //when
        listener.onSelectionChanged(event);
        //then
        then(newItem).should()
                     .publishAsSelected();
    }

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    public void shouldOnOuSelected() throws Exception {
        //given
        val ou = OU.of(LdapNameUtil.parse("ou=users"), "users");
        val ouItem = OuNavigationItem.of(ou, applicationEventPublisher);
        val event = NavigationItemOuSelectedEvent.of(ouItem);
        //when
        listener.onOuSelected(event);
        //then
        //...method only does logging for now
    }

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    public void shouldOnUserSelected() throws Exception {
        //given
        val user = User.builder()
                       .dn(LdapNameUtil.parse("cn=bob"))
                       .cn("bob")
                       .sn("smith")
                       .build();
        val userItem = UserNavigationItem.of(user, applicationEventPublisher);
        val event = NavigationItemUserSelectedEvent.of(userItem);
        //when
        listener.onUserSelected(event);
        //then
        //...method only does logging for now
    }
}
