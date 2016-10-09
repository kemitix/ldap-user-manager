package net.kemitix.ldapmanager.ui;

import lombok.val;
import net.kemitix.ldapmanager.util.nameditem.NamedItem;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link NavigationPanel}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class NavigationPanelTest {

    private NavigationPanel navigationPanel;

    private ArrayList<NamedItem<Runnable>> namedItems;

    private AtomicReference<String> selectedItem = new AtomicReference<>("unselected");

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        namedItems = new ArrayList<>();
        navigationPanel = new NavigationPanel(() -> namedItems);
        navigationPanel.init();
    }

    @Test
    public void findItemPositionByNameShouldFindItemWhenPresent() {
        //given
        givenPopulatedList();
        //when
        val result = navigationPanel.findItemPositionByName("beta");
        //then
        assertThat(result).contains(1);
    }

    @Test
    public void findItemPositionByNameShouldFindNothingWhenNotPresent() {
        //given
        givenPopulatedList();
        //when
        val result = navigationPanel.findItemPositionByName("gamma");
        //then
        assertThat(result).isEmpty();
    }

    @Test
    public void findAndSelectItemByNameShouldSelectItemWhenPresent() {
        //given
        givenPopulatedList();
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

    private void givenPopulatedList() {
        namedItems.add(0, NamedItem.of("alpha", () -> selectedItem.getAndSet("alpha")));
        namedItems.add(1, NamedItem.of("beta", () -> selectedItem.getAndSet("beta")));
        navigationPanel.onCurrentContainerChangedEventUpdateNavigationItems();
    }
}
