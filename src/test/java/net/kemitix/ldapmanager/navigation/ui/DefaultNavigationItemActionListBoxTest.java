package net.kemitix.ldapmanager.navigation.ui;

import com.googlecode.lanterna.gui2.Interactable;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import lombok.val;
import net.kemitix.ldapmanager.Messages;
import net.kemitix.ldapmanager.navigation.NavigationItem;
import net.kemitix.ldapmanager.navigation.events.NavigationItemSelectionChangedEvent;
import net.kemitix.ldapmanager.ui.StartupExceptionsCollector;
import net.kemitix.ldapmanager.util.nameditem.NamedItem;
import org.assertj.core.api.Assertions;
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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
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

    public static final String ITEM_NAME_2 = "beta";

    public static final String ITEM_NAME_OTHER = "gamma";

    public static final String ITEM_NAME_1 = "name";

    private static final char CHAR_SPACE = ' ';

    private static final char CHAR_X = 'x';

    private DefaultNavigationItemActionListBox navigationItemListBox;

    private List<NamedItem<NavigationItem>> namedItems;

    @Mock
    private Supplier<List<NamedItem<NavigationItem>>> navigationItemsSupplier;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @Mock
    private StartupExceptionsCollector startupExceptionsCollector;

    private final AtomicReference<String> selectedItem = new AtomicReference<>("unselected");

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private KeyStroke escapeKeyStroke;

    private KeyStroke enterKeyStroke;

    private KeyStroke spaceKeyStroke;

    private KeyStroke xKeyStroke;

    @Captor
    private ArgumentCaptor<NavigationItemSelectionChangedEvent> eventCaptor;

    private NamedItem<NavigationItem> namedNavigationItem0;

    private NamedItem<NavigationItem> namedNavigationItem1;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        navigationItemListBox =
                new DefaultNavigationItemActionListBox(navigationItemsSupplier, startupExceptionsCollector,
                                                       applicationEventPublisher
                );
        namedItems = new ArrayList<>();
        given(navigationItemsSupplier.get()).willReturn(namedItems);
        escapeKeyStroke = new KeyStroke(KeyType.Escape);
        enterKeyStroke = new KeyStroke(KeyType.Enter);
        xKeyStroke = new KeyStroke(CHAR_X, false, false);
        spaceKeyStroke = new KeyStroke(CHAR_SPACE, false, false);
        assertThat(spaceKeyStroke.getKeyType()).isEqualTo(KeyType.Character);
        assertThat(spaceKeyStroke.getCharacter()).isEqualTo(CHAR_SPACE);
        namedNavigationItem0 = createItem(ITEM_NAME_1);
        namedNavigationItem1 = createItem(ITEM_NAME_2);
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
        val result = navigationItemListBox.findItemPositionByName(ITEM_NAME_2);
        //then
        assertThat(result).contains(1);
    }

    @Test
    public void findItemPositionByNameShouldFindNothingWhenNotPresent() {
        //given
        givenPopulatedList();
        //when
        val result = navigationItemListBox.findItemPositionByName(ITEM_NAME_OTHER);
        //then
        assertThat(result).isEmpty();
    }

    @Test
    public void findAndSelectItemByNameShouldSelectItemWhenPresent() {
        //given
        givenPopulatedList();
        //when
        val result = navigationItemListBox.findAndSelectItemByName(ITEM_NAME_2);
        //then
        Assertions.assertThat(result.map(Runnable::toString))
                  .contains(ITEM_NAME_2);
        result.ifPresent(a -> navigationItemListBox.performSelectedItem());
        assertThat(selectedItem.get()).isEqualTo(ITEM_NAME_2);
    }

    @Test
    public void findAndSelectItemByNameShouldLeaveSelectionAlonItemWhenNotPresent() {
        //given
        givenPopulatedList();
        //when
        val result = navigationItemListBox.findAndSelectItemByName(ITEM_NAME_OTHER);
        //then
        Assertions.assertThat(result)
                  .isEmpty();
        assertThat(selectedItem.get()).isEqualTo("unselected");
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
        assertThat(event.getOldItem()).contains(namedNavigationItem0.getItem());
        assertThat(event.getNewItem()).contains(namedNavigationItem1.getItem());
    }

    @Test
    public void onSelectionChangeToNone() {
        //given
        givenPopulatedList();
        //when
        navigationItemListBox.onSelectionChange(0, -1);
        //then
        then(applicationEventPublisher).should()
                                       .publishEvent(eventCaptor.capture());
        final NavigationItemSelectionChangedEvent event = eventCaptor.getValue();
        assertThat(event.getOldItem()).contains(namedNavigationItem0.getItem());
        assertThat(event.getNewItem()).isEmpty();
    }

    @Test
    public void onSelectionChangeFromNone() {
        //given
        givenPopulatedList();
        //when
        navigationItemListBox.onSelectionChange(-1, 1);
        //then
        then(applicationEventPublisher).should()
                                       .publishEvent(eventCaptor.capture());
        final NavigationItemSelectionChangedEvent event = eventCaptor.getValue();
        assertThat(event.getOldItem()).isEmpty();
        assertThat(event.getNewItem()).contains(namedNavigationItem1.getItem());
    }

    @Test
    public void onSelectionChangeWhenListEmpty() {
        //given
        givenUnpopulatedList();
        //when
        navigationItemListBox.onSelectionChange(0, 1);
        //then
        then(applicationEventPublisher).should()
                                       .publishEvent(eventCaptor.capture());
        final NavigationItemSelectionChangedEvent event = eventCaptor.getValue();
        assertThat(event.getOldItem()).isEmpty();
        assertThat(event.getNewItem()).isEmpty();
    }

    private void givenPopulatedList() {
        given(navigationItemsSupplier.get()).willReturn(namedItems);
        namedItems.add(namedNavigationItem0);
        namedItems.add(namedNavigationItem1);
        navigationItemListBox.init();
    }

    private void givenUnpopulatedList() {
        navigationItemListBox.init();
    }

    private NamedItem<NavigationItem> createItem(final String label) {
        final NavigationItem myItem =
                new DefaultNavigationItemActionListBoxTest.MyItem(label, applicationEventPublisher, selectedItem);
        return NamedItem.of(label, myItem);
    }

    private static class MyItem implements NavigationItem {

        private final String name;

        private final ApplicationEventPublisher applicationEventPublisher;

        private final AtomicReference<String> selectedItem;

        MyItem(
                final String name, final ApplicationEventPublisher applicationEventPublisher,
                final AtomicReference<String> selectedItem
              ) {
            this.name = name;
            this.applicationEventPublisher = applicationEventPublisher;
            this.selectedItem = selectedItem;
        }

        @Override
        public void run() {
            selectedItem.getAndSet(name);
        }

        @Override
        public String toString() {
            return name;
        }

        @Override
        public void publishAsSelected() {
            applicationEventPublisher.publishEvent(this);
        }
    }
}
