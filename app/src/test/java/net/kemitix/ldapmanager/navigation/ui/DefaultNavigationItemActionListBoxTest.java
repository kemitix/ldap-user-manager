package net.kemitix.ldapmanager.navigation.ui;

import com.googlecode.lanterna.gui2.Interactable;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import lombok.val;
import net.kemitix.ldapmanager.Messages;
import net.kemitix.ldapmanager.domain.OU;
import net.kemitix.ldapmanager.domain.User;
import net.kemitix.ldapmanager.ldap.LdapNameUtil;
import net.kemitix.ldapmanager.navigation.NavigationItem;
import net.kemitix.ldapmanager.navigation.NavigationItemFactory;
import net.kemitix.ldapmanager.navigation.OuNavigationItem;
import net.kemitix.ldapmanager.navigation.UserNavigationItem;
import net.kemitix.ldapmanager.navigation.events.NavigationItemSelectionChangedEvent;
import net.kemitix.ldapmanager.ui.StartupExceptionsCollector;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.ldap.AuthenticationException;

import javax.naming.Name;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;

/**
 * Tests for {@link DefaultNavigationItemActionListBox}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class DefaultNavigationItemActionListBoxTest {

    private static final String NOT_FOUND_NAME = "gamma";

    private static final String USER_NAME = "alpha";

    private static final String OU_NAME = "beta";

    private static final char CHAR_SPACE = ' ';

    private static final char CHAR_X = 'x';

    private static final int INDEX_FIRST_ITEM = 0;

    private static final int INDEX_NONE_SELECTED = -1;

    private static final int INDEX_SECOND_ITEM = 1;

    private DefaultNavigationItemActionListBox navigationItemListBox;

    private List<NavigationItem> navigationItems;

    @Mock
    private Supplier<List<NavigationItem>> navigationItemsSupplier;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @Mock
    private StartupExceptionsCollector startupExceptionsCollector;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private KeyStroke escapeKeyStroke;

    private KeyStroke enterKeyStroke;

    private KeyStroke spaceKeyStroke;

    private KeyStroke xKeyStroke;

    @Captor
    private ArgumentCaptor<NavigationItemSelectionChangedEvent> eventCaptor;

    private UserNavigationItem navigationItemUser;

    private OuNavigationItem navigationItemOU;

    private Name userDn = LdapNameUtil.parse("dn=" + USER_NAME);

    private Name ouDn = LdapNameUtil.parse("dn=" + OU_NAME);

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        navigationItemListBox =
                new DefaultNavigationItemActionListBox(navigationItemsSupplier, startupExceptionsCollector,
                                                       applicationEventPublisher
                );
        navigationItems = new ArrayList<>();
        given(navigationItemsSupplier.get()).willReturn(navigationItems);
        escapeKeyStroke = new KeyStroke(KeyType.Escape);
        enterKeyStroke = new KeyStroke(KeyType.Enter);
        xKeyStroke = new KeyStroke(CHAR_X, false, false);
        spaceKeyStroke = new KeyStroke(CHAR_SPACE, false, false);
        assertThat(spaceKeyStroke.getKeyType()).isEqualTo(KeyType.Character);
        assertThat(spaceKeyStroke.getCharacter()).isEqualTo(CHAR_SPACE);
        navigationItemUser =
                UserNavigationItem.create(User.create(userDn, USER_NAME, "userSn"), applicationEventPublisher);
        navigationItemOU = OuNavigationItem.create(OU.of(ouDn, OU_NAME), applicationEventPublisher);
    }

    @Test
    public void thereIsNoCursorLocation() {
        assertThat(navigationItemListBox.getCursorLocation()).as("Has no cursor, so no location")
                                                             .isNull();
    }

    @Test
    public void findAndSelectItemByNameWithNullNameShouldThrowNPE() {
        //given
        exception.expect(NullPointerException.class);
        //when
        navigationItemListBox.findAndSelectItemByName(null);
    }

    @Test
    public void findItemPositionByNameWithNullNameShouldThrowNPE() {
        //given
        exception.expect(NullPointerException.class);
        //when
        navigationItemListBox.findItemPositionByName(null);
    }

    @Test
    public void findItemPositionByNameShouldFindItemWhenPresent() {
        //given
        givenPopulatedList();
        //when
        val result = navigationItemListBox.findItemPositionByName(OU_NAME);
        //then
        assertThat(result).contains(INDEX_FIRST_ITEM);
    }

    @Test
    public void findItemPositionByNameShouldFindNothingWhenNotPresent() {
        //given
        givenPopulatedList();
        //when
        val result = navigationItemListBox.findItemPositionByName(NOT_FOUND_NAME);
        //then
        assertThat(result).isEmpty();
    }

    @Test
    public void findAndSelectItemByNameShouldSelectItemWhenPresent() {
        //given
        givenPopulatedList();
        //when
        val result = navigationItemListBox.findAndSelectItemByName(OU_NAME);
        //then
        assertThat(result.map(NavigationItem::getName)).contains(OU_NAME);
    }

    @Test
    public void findAndSelectItemByNameShouldLeaveSelectionAlonItemWhenNotPresent() {
        //given
        givenPopulatedList();
        //when
        val result = navigationItemListBox.findAndSelectItemByName(NOT_FOUND_NAME);
        //then
        assertThat(result).isEmpty();
    }

    @Test
    public void initShouldCollectAuthenticationException() {
        //given
        willThrow(AuthenticationException.class).given(navigationItemsSupplier)
                                                .get();
        //when
        navigationItemListBox.init();
        //then
        then(startupExceptionsCollector).should()
                                        .addException(eq(Messages.ERROR_AUTHENTICATION.getValue()),
                                                      any(AuthenticationException.class)
                                                     );
    }

    @Test
    public void escapeKeyIsUnhandled() {
        //given
        givenPopulatedList();
        //when
        final Interactable.Result result = navigationItemListBox.onHandleKeyStroke(escapeKeyStroke);
        //then
        assertThat(result).isEqualTo(Interactable.Result.UNHANDLED);
    }

    @Test
    public void xKeyIsUnhandled() {
        //given
        givenPopulatedList();
        //when
        final Interactable.Result result = navigationItemListBox.onHandleKeyStroke(xKeyStroke);
        //then
        assertThat(result).isEqualTo(Interactable.Result.UNHANDLED);
    }

    @Test
    public void spaceKeyIsHandledWhenItemSelected() {
        //given
        givenPopulatedList();
        //when
        final Interactable.Result result = navigationItemListBox.onHandleKeyStroke(spaceKeyStroke);
        //then
        assertThat(result).isEqualTo(Interactable.Result.HANDLED);
    }

    @Test
    public void enterKeyIsHandledWhenItemSelected() {
        //given
        givenPopulatedList();
        //when
        final Interactable.Result result = navigationItemListBox.onHandleKeyStroke(enterKeyStroke);
        //then
        assertThat(result).isEqualTo(Interactable.Result.HANDLED);
    }

    @Test
    public void spaceKeyIsUnhandledWhenNoItemSelected() {
        //given
        givenUnpopulatedList();
        //when
        final Interactable.Result result = navigationItemListBox.onHandleKeyStroke(spaceKeyStroke);
        //then
        assertThat(result).isEqualTo(Interactable.Result.UNHANDLED);
    }

    @Test
    public void enterKeyIsUnhandledWhenNoItemSelected() {
        //given
        givenUnpopulatedList();
        //when
        final Interactable.Result result = navigationItemListBox.onHandleKeyStroke(enterKeyStroke);
        //then
        assertThat(result).isEqualTo(Interactable.Result.UNHANDLED);
    }

    @Test
    public void onSelectionChangeNormal() {
        //given
        givenPopulatedList();
        //when
        navigationItemListBox.onSelectionChange(0, 1);
        //then
        then(applicationEventPublisher).should()
                                       .publishEvent(eventCaptor.capture());
        final NavigationItemSelectionChangedEvent event = eventCaptor.getValue();
        assertThat(event.getOldItem()).contains(navigationItemOU);
        assertThat(event.getNewItem()).contains(navigationItemUser);
    }

    @Test
    public void onSelectionChangeToNone() {
        //given
        givenPopulatedList();
        //when
        navigationItemListBox.onSelectionChange(INDEX_FIRST_ITEM, INDEX_NONE_SELECTED);
        //then
        then(applicationEventPublisher).should()
                                       .publishEvent(eventCaptor.capture());
        final NavigationItemSelectionChangedEvent event = eventCaptor.getValue();
        assertThat(event.getOldItem()).contains(navigationItemOU);
        assertThat(event.getNewItem()).isEmpty();
    }

    @Test
    public void onSelectionChangeFromNone() {
        //given
        givenPopulatedList();
        //when
        navigationItemListBox.onSelectionChange(INDEX_NONE_SELECTED, INDEX_FIRST_ITEM);
        //then
        then(applicationEventPublisher).should()
                                       .publishEvent(eventCaptor.capture());
        final NavigationItemSelectionChangedEvent event = eventCaptor.getValue();
        assertThat(event.getOldItem()).isEmpty();
        assertThat(event.getNewItem()).contains(navigationItemOU);
    }

    @Test
    public void onSelectionChangeWhenListEmpty() {
        //given
        givenUnpopulatedList();
        //when
        navigationItemListBox.onSelectionChange(INDEX_FIRST_ITEM, INDEX_SECOND_ITEM);
        //then
        then(applicationEventPublisher).should()
                                       .publishEvent(eventCaptor.capture());
        final NavigationItemSelectionChangedEvent event = eventCaptor.getValue();
        assertThat(event.getOldItem()).isEmpty();
        assertThat(event.getNewItem()).isEmpty();
    }

    @SuppressWarnings("ObjectToString")
    @Test
    public void shouldShowOusWithBraces() {
        //given
        val ou = NavigationItemFactory.create(OU.of(LdapNameUtil.empty(), "users"), applicationEventPublisher);
        val user = NavigationItemFactory.create(User.builder()
                                                    .dn(LdapNameUtil.parse("cn=bob"))
                                                    .cn("bob")
                                                    .sn("smith")
                                                    .build(), applicationEventPublisher);
        //then
        assertThat(ou.toString()).contains("ou=users", "name=users", "featureSet=[RENAME]");
        assertThat(ou.getName()).isEqualTo("users");
        assertThat(user.toString()).contains("cn=bob", "dn=cn=bob", "name=bob");
        assertThat(user.getName()).isEqualTo("bob");
    }

    private void givenPopulatedList() {
        given(navigationItemsSupplier.get()).willReturn(navigationItems);
        navigationItems.add(navigationItemUser);
        navigationItems.add(navigationItemOU);
        navigationItemListBox.init();
        navigationItemListBox.getItems()
                             .stream()
                             .map(NavigationItem::getSortableName)
                             .forEach(System.out::println);
    }

    private void givenUnpopulatedList() {
        navigationItemListBox.init();
    }
}
