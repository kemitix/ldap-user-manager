package net.kemitix.ldapmanager.ui;

import com.googlecode.lanterna.gui2.ActionListBox;
import com.googlecode.lanterna.gui2.Border;
import com.googlecode.lanterna.gui2.Component;
import com.googlecode.lanterna.gui2.Panel;
import net.kemitix.ldapmanager.util.nameditem.NamedItem;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link NavigationPanel}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class NavigationPanelTest {

    private NavigationPanel navigationPanel;

    private Supplier<List<NamedItem<Runnable>>> navigationItemSupplier;

    private ArrayList<NamedItem<Runnable>> namedItems;

    @Before
    public void setUp() throws Exception {
        namedItems = new ArrayList<>();
        navigationItemSupplier = () -> namedItems;
        navigationPanel = new NavigationPanel(navigationItemSupplier);
    }

    @Test
    public void shouldInit() throws Exception {
        //given
        final AtomicBoolean flag = new AtomicBoolean(false);
        namedItems.add(NamedItem.of("bob", () -> flag.getAndSet(true)));
        //when
        navigationPanel.init();
        //then
        final Collection<Component> children = navigationPanel.getChildren();
        assertThat(children).hasSize(1);
        children.forEach(child -> {
            assertThat(child).isInstanceOf(Border.class);
            Component component = ((Border) child).getComponent();
            assertThat(component).isInstanceOf(Panel.class);
            final Collection<Component> innerChildren = ((Panel) component).getChildren();
            assertThat(innerChildren).hasSize(1);
            innerChildren.forEach(innerChild -> {
                assertThat(innerChild).isInstanceOf(ActionListBox.class);
                final List<Runnable> runnableList = ((ActionListBox) innerChild).getItems();
                assertThat(runnableList).hasSize(1);
                assertThat(flag.get()).isFalse();
                runnableList.get(0)
                         .run();
                assertThat(flag.get()).isTrue();
            });
        });
    }

}
