package net.kemitix.ldapmanager.actions.user.rename;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import lombok.val;
import net.kemitix.ldapmanager.domain.OU;
import net.kemitix.ldapmanager.domain.User;
import net.kemitix.ldapmanager.ldap.LdapNameUtil;
import net.kemitix.ldapmanager.ldap.LdapService;
import net.kemitix.ldapmanager.navigation.OuNavigationItem;
import net.kemitix.ldapmanager.navigation.UserNavigationItem;
import net.kemitix.ldapmanager.navigation.events.NavigationItemSelectionChangedEvent;
import net.kemitix.ldapmanager.navigation.events.NavigationItemUserSelectedEvent;
import net.kemitix.ldapmanager.ui.RenameDnDialog;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

/**
 * Tests for {@link RenameUserKeyStrokeHandler}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class RenameUserKeyStrokeHandlerTest {

    private RenameUserKeyStrokeHandler handler;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @Mock
    private RenameDnDialog renameDnDialog;

    @Mock
    private LdapService ldapService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        handler = new RenameUserKeyStrokeHandler(applicationEventPublisher, renameDnDialog, ldapService);
    }

    @Test
    public void shouldGetDescription() throws Exception {
        assertThat(handler.getDescription()).isEqualTo("Rename User");
    }

    @Test
    public void shouldGetKey() throws Exception {
        assertThat(handler.getKey()).isEqualTo("r");
    }

    @Test
    public void shouldCanHandleKeyValid() throws Exception {
        assertThat(handler.canHandleKey(new KeyStroke('r', false, false))).isTrue();
    }

    @Test
    public void shouldCanHandleKeyInValid() throws Exception {
        assertThat(handler.canHandleKey(new KeyStroke('x', false, false))).isFalse();
        assertThat(handler.canHandleKey(new KeyStroke(KeyType.Escape))).isFalse();
    }

    @Test
    public void shouldHandleInputWhenUserSelected() throws Exception {
        //given
        val user = User.builder()
                       .build();
        val dn = LdapNameUtil.empty();
        given(renameDnDialog.getRenamedDn(user.getDn())).willReturn(Optional.of(dn));
        /// inject user into handler
        handler.onUserSelectedEvent(
                NavigationItemUserSelectedEvent.of(UserNavigationItem.create(user, applicationEventPublisher)));
        //when
        handler.handleInput(new KeyStroke('r', false, false));
        //then
        then(ldapService).should()
                         .rename(user, dn);
    }

    @Test
    public void shouldBecomeActiveOnUserSelectedEvent() throws Exception {
        //given
        assertThat(handler.isActive()).as("inactive before event")
                                      .isFalse();
        //when
        handler.onUserSelectedEvent(NavigationItemUserSelectedEvent.of(UserNavigationItem.create(
                User.builder()
                    .build(), applicationEventPublisher)));
        //then
        assertThat(handler.isActive()).as("active after event")
                                      .isTrue();
    }

    @Test
    public void shouldBecomeInactiveOnSelectionChanged() throws Exception {
        //given
        val existingItem = UserNavigationItem.create(User.builder()
                                                         .build(), applicationEventPublisher);
        val unsupportedItem = OuNavigationItem.create(OU.builder()
                                                        .build(), applicationEventPublisher);
        handler.onUserSelectedEvent(NavigationItemUserSelectedEvent.of(existingItem));
        assertThat(handler.isActive()).as("active before event")
                                      .isTrue();
        //when
        handler.onSelectionChanged(NavigationItemSelectionChangedEvent.of(existingItem, unsupportedItem));
        //then
        assertThat(handler.isActive()).as("inactive after event")
                                      .isFalse();
    }
}
