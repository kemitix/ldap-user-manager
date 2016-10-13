package net.kemitix.ldapmanager.navigation;

import lombok.val;
import net.kemitix.ldapmanager.ui.StartupExceptionsCollector;
import net.kemitix.ldapmanager.util.nameditem.NamedItem;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
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

    private DefaultNavigationItemActionListBox actionListBox;

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

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        actionListBox = new DefaultNavigationItemActionListBox(navigationItemsSupplier, startupExceptionsCollector);
        namedItems = new ArrayList<>();
        given(navigationItemsSupplier.get()).willReturn(namedItems);
    }

    @Test
    public void init() throws Exception {

    }

    @Test
    public void addItem() throws Exception {

    }

    @Test
    public void addItem1() throws Exception {

    }

    @Test
    public void addItem2() throws Exception {

    }

    @Test
    public void onCurrentContainerChanged() throws Exception {

    }

    @Test
    public void findAndSelectItemByName() throws Exception {

    }

    @Test
    public void findItemPositionByName() throws Exception {

    }

    @Test
    public void performSelectedItem() throws Exception {

    }

    @Test
    public void findAndSelectItemByNameWithNullShouldThrowNPE() {
        //given
        exception.expect(NullPointerException.class);
        //when
        actionListBox.findAndSelectItemByName(null);
    }

    @Test
    public void findItemPositionByNameWithNullShouldThrowNPE() {
        //given
        exception.expect(NullPointerException.class);
        //when
        actionListBox.findItemPositionByName(null);
    }

    @Test
    public void findItemPositionByNameShouldFindItemWhenPresent() {
        //given
        givenPopulatedList();
        actionListBox.init();
        //when
        val result = actionListBox.findItemPositionByName("beta");
        //then
        assertThat(result).contains(1);
    }

    @Test
    public void findItemPositionByNameShouldFindNothingWhenNotPresent() {
        //given
        givenPopulatedList();
        actionListBox.init();
        //when
        val result = actionListBox.findItemPositionByName("gamma");
        //then
        assertThat(result).isEmpty();
    }

    @Test
    public void findAndSelectItemByNameShouldSelectItemWhenPresent() {
        //given
        givenPopulatedList();
        actionListBox.init();
        //when
        val result = actionListBox.findAndSelectItemByName("beta");
        //then
        assertThat(result.map(Runnable::toString)).contains("beta");
        result.ifPresent(a -> actionListBox.performSelectedItem());
        assertThat(selectedItem.get()).isEqualTo("beta");
    }

    @Test
    public void findAndSelectItemByNameShouldLeaveSelectionAlonItemWhenNotPresent() {
        //given
        givenPopulatedList();
        actionListBox.init();
        //when
        val result = actionListBox.findAndSelectItemByName("gamma");
        //then
        assertThat(result).isEmpty();
        assertThat(selectedItem.get()).isEqualTo("unselected");
    }

    @Test
    public void initShouldCollectAuthenticationException() {
        //given
        willThrow(AuthenticationException.class).given(navigationItemsSupplier)
                                                .get();
        //when
        actionListBox.init();
        //then
        then(startupExceptionsCollector).should()
                                        .addException(eq("Authentication error"), any(AuthenticationException.class));
    }

    private void givenPopulatedList() {
        given(navigationItemsSupplier.get()).willReturn(namedItems);
        addItem("name");
        addItem("beta");
    }

    private void addItem(final String alpha) {
        namedItems.add(NamedItem.of(alpha, new MyAbstractNavigationItem(alpha)));
    }

    private class MyAbstractNavigationItem extends AbstractNavigationItem implements NavigationItem {

        private final String name;

        public MyAbstractNavigationItem(final String name) {
            super(name, applicationEventPublisher);
            this.name = name;
        }

        @Override
        public void run() {
            selectedItem.getAndSet(name);
        }
    }
}
