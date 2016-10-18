package net.kemitix.ldapmanager.context;

import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.ActionListDialogBuilder;
import lombok.val;
import net.kemitix.ldapmanager.navigation.NavigationItem;
import net.kemitix.ldapmanager.ui.ActionListDialogBuilderFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

/**
 * Tests for {@link DefaultContextMenu}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class DefaultContextMenuTest {

    private DefaultContextMenu contextMenu;

    @Mock
    private WindowBasedTextGUI gui;

    @Mock
    private ActionListDialogBuilderFactory dialogBuilderFactory;

    private Set<MenuItemFactory> menuItemsFactories;

    @Mock
    private MenuItemFactory menuItemFactory;

    @Mock
    private NavigationItem navigationItem;

    @Spy
    private ActionListDialogBuilder actionListDialogBuilder = new MyActionListDialogBuilder();

    @Mock
    private MenuItem menuItem;

    @Mock
    private Runnable action;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        menuItemsFactories = new HashSet<>();
    }

    @Test
    public void display() throws Exception {
        //given
        prepareCallToDisplay();
        //when
        contextMenu.display(navigationItem);

        //then
        then(actionListDialogBuilder).should()
                                     .addAction("label", action);
        then(actionListDialogBuilder).should().setTitle("item");
    }

    @Test
    public void onDisplayContextMenu() throws Exception {
        //given
        prepareCallToDisplay();
        val event = DisplayContextMenuEvent.of(navigationItem);
        //when
        contextMenu.onDisplayContextMenu(event);
        //then
        then(actionListDialogBuilder).should()
                                     .addAction("label", action);
        then(actionListDialogBuilder).should().setTitle("item");
    }

    private void prepareCallToDisplay() {
        menuItemsFactories.add(menuItemFactory);
        contextMenu = new DefaultContextMenu(gui, dialogBuilderFactory, menuItemsFactories);

        given(dialogBuilderFactory.create()).willReturn(actionListDialogBuilder);
        given(menuItemFactory.create(navigationItem)).willReturn(Stream.of(menuItem));
        given(menuItem.getLabel()).willReturn("label");
        given(menuItem.getAction()).willReturn(action);
        given(navigationItem.getName()).willReturn("item");
    }

    private class MyActionListDialogBuilder extends ActionListDialogBuilder {

    }
}
