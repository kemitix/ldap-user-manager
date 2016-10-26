package net.kemitix.ldapmanager.popupmenus.context;

import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.ActionListDialog;
import com.googlecode.lanterna.gui2.dialogs.ActionListDialogBuilder;
import lombok.val;
import net.kemitix.ldapmanager.navigation.NavigationItem;
import net.kemitix.ldapmanager.popupmenus.MenuItem;
import net.kemitix.ldapmanager.popupmenus.MenuItemFactory;
import net.kemitix.ldapmanager.popupmenus.MenuItemType;
import net.kemitix.ldapmanager.popupmenus.PopupMenu;
import net.kemitix.ldapmanager.ui.ActionListDialogBuilderFactory;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import javax.naming.Name;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

/**
 * Tests for {@link PopupMenu}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class ContextMenuTest {

    private ContextMenu contextMenu;

    @Rule
    public ExpectedException exception = ExpectedException.none();

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
    private ActionListDialogBuilder dialogBuilder = new MyActionListDialogBuilder();

    @Mock
    private MenuItem menuItem;

    @Mock
    private Runnable action;

    @Mock
    private Name dn;

    @Mock
    private ActionListDialog dialog;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        menuItemsFactories = new HashSet<>();
        contextMenu = new ContextMenu(gui, dialogBuilderFactory, menuItemsFactories);
    }

    @Test
    public void display() throws Exception {
        //given
        prepareCallToDisplay();
        //when
        contextMenu.display(dn, "item");
        //then
        then(dialogBuilder).should()
                           .addAction("label", action);
        then(dialogBuilder).should()
                           .setTitle("item");
    }

    @Test
    public void onDisplayContextMenu() throws Exception {
        //given
        prepareCallToDisplay();
        val event = DisplayContextMenuEvent.of(dn, "item");
        //when
        contextMenu.onDisplayContextMenu(event);
        //then
        then(dialogBuilder).should()
                           .addAction("label", action);
        then(dialogBuilder).should()
                           .setTitle("item");
    }

    private void prepareCallToDisplay() {
        menuItemsFactories.add(menuItemFactory);
        contextMenu = new ContextMenu(gui, dialogBuilderFactory, menuItemsFactories);
        given(navigationItem.getDn()).willReturn(dn);

        given(dialogBuilderFactory.create()).willReturn(dialogBuilder);
        given(menuItemFactory.create(dn, contextMenu)).willReturn(Stream.of(menuItem));
        given(menuItem.getLabel()).willReturn("label");
        given(menuItem.getAction()).willReturn(action);
        given(navigationItem.getName()).willReturn("item");
    }

    @Test
    public void displayShouldNoShowWhenNothingToInsert() {
        //given
        given(dialogBuilderFactory.create()).willReturn(dialogBuilder);
        given(menuItemFactory.create(dn, contextMenu)).willReturn(Stream.empty());
        //when
        contextMenu.onDisplayContextMenu(DisplayContextMenuEvent.of(dn, "title"));
        //then
        then(dialog).should(never())
                    .showDialog(gui);
    }

    @Test
    public void canReceiveWillNotAcceptCreateMenuItems() {
        assertThat(contextMenu.canReceive(MenuItemType.CREATE)).isFalse();
    }

    @Test
    public void canReceiveWillAcceptModifyMenuItems() {
        assertThat(contextMenu.canReceive(MenuItemType.MODIFY)).isTrue();
    }

    @Test
    public void canReceiveWillThrowNPEWhenTypeIsNull() {
        //given
        exception.expect(NullPointerException.class);
        exception.expectMessage("type");
        //when
        contextMenu.canReceive(null);
    }

    private class MyActionListDialogBuilder extends ActionListDialogBuilder {

    }
}
