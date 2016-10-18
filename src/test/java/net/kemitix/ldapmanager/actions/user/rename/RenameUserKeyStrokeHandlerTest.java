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
import static org.mockito.Matchers.any;

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
    public void shouldPublishRenameRequestEventHandleInput() throws Exception {
        //given
        val keyStroke = new KeyStroke('r', false, false);
        //when
        handler.handleInput(keyStroke);
        //then
        then(applicationEventPublisher).should()
                                       .publishEvent(any(RenameUserRequestEvent.class));
    }

    @Test
    public void shouldHandleInputWhenUserSelected() throws Exception {
        //given
        val user = User.builder()
                       .dn(LdapNameUtil.parse("cn=bob,ou=users"))
                       .build();
        val dn = LdapNameUtil.parse("cn=bob,ou=users");
        given(renameDnDialog.getRenamedDn(user.getDn())).willReturn(Optional.of(dn));
        /// inject user into handler
        val userNavigationItem = UserNavigationItem.create(user, applicationEventPublisher);
        val event = RenameUserRequestEvent.create(userNavigationItem);
        //when
        handler.onRenameUserRequest(event);
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
