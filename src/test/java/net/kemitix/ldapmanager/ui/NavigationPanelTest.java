package net.kemitix.ldapmanager.ui;

import lombok.val;
import net.kemitix.ldapmanager.util.nameditem.NamedItem;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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
 * Tests for {@link NavigationPanel}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class NavigationPanelTest {

    private NavigationPanel navigationPanel;

    @Mock
    private StartupExceptionsCollector startupExceptionsCollector;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Mock
    private Supplier<List<NamedItem<Runnable>>> navigationItemsSupplier;

    private List<NamedItem<Runnable>> namedItems;

    private final AtomicReference<String> selectedItem = new AtomicReference<>("unselected");

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        navigationPanel = new NavigationPanel(navigationItemsSupplier, startupExceptionsCollector);
        namedItems = new ArrayList<>();
    }

    @Test
    public void findItemPositionByNameShouldFindItemWhenPresent() {
        //given
        givenPopulatedList();
        navigationPanel.init();
        //when
        val result = navigationPanel.findItemPositionByName("beta");
        //then
        assertThat(result).contains(1);
    }

    @Test
    public void findItemPositionByNameShouldFindNothingWhenNotPresent() {
        //given
        givenPopulatedList();
        navigationPanel.init();
        //when
        val result = navigationPanel.findItemPositionByName("gamma");
        //then
        assertThat(result).isEmpty();
    }

    @Test
    public void findAndSelectItemByNameShouldSelectItemWhenPresent() {
        //given
        givenPopulatedList();
        navigationPanel.init();
        //when
        val result = navigationPanel.findAndSelectItemByName("beta");
        //then
        assertThat(result.map(Runnable::toString)).contains("beta");
        result.ifPresent(a -> navigationPanel.performSelectedItem());
        assertThat(selectedItem.get()).isEqualTo("beta");
    }

    @Test
    public void findAndSelectItemByNameShouldLeaveSelectionAlonItemWhenNotPresent() {
        //given
        givenPopulatedList();
        navigationPanel.init();
        //when
        val result = navigationPanel.findAndSelectItemByName("gamma");
        //then
        assertThat(result).isEmpty();
        assertThat(selectedItem.get()).isEqualTo("unselected");
    }

    @Test
    public void findAndSelectItemByNameWithNullShouldThrowNPE() {
        //given
        exception.expect(NullPointerException.class);
        //when
        navigationPanel.findAndSelectItemByName(null);
    }

    @Test
    public void findItemPositionByNameWithNullShouldThrowNPE() {
        //given
        exception.expect(NullPointerException.class);
        //when
        navigationPanel.findItemPositionByName(null);
    }

    @Test
    public void initShouldCollectAuthenticationException() {
        //given
        willThrow(AuthenticationException.class).given(navigationItemsSupplier)
                                                .get();
        //when
        navigationPanel.init();
        //then
        then(startupExceptionsCollector).should()
                                        .addException(eq("Authentication error"), any(AuthenticationException.class));
    }

    private void givenPopulatedList() {
        given(navigationItemsSupplier.get()).willReturn(namedItems);
        namedItems.add(0, NamedItem.of("alpha", () -> selectedItem.getAndSet("alpha")));
        namedItems.add(1, NamedItem.of("beta", () -> selectedItem.getAndSet("beta")));
    }
}
