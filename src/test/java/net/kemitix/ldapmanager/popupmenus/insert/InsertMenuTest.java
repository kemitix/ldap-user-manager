package net.kemitix.ldapmanager.popupmenus.insert;

import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.ActionListDialog;
import com.googlecode.lanterna.gui2.dialogs.ActionListDialogBuilder;
import net.kemitix.ldapmanager.popupmenus.MenuItem;
import net.kemitix.ldapmanager.popupmenus.MenuItemFactory;
import net.kemitix.ldapmanager.popupmenus.MenuItemType;
import net.kemitix.ldapmanager.ui.ActionListDialogBuilderFactory;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.naming.Name;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

/**
 * Tests for {@link InsertMenu}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class InsertMenuTest {

    private InsertMenu insertMenu;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Mock
    private WindowBasedTextGUI gui;

    @Mock
    private ActionListDialogBuilderFactory dialogBuilderFactory;

    private Set<MenuItemFactory> menuItemFactories = new HashSet<>();

    @Mock
    private Name dn;

    private ActionListDialogBuilder dialogBuilder;

    @Mock
    private MenuItemFactory menuItemFactory;

    @Mock
    private MenuItem menuItem;

    @Mock
    private Runnable action;

    @Mock
    private ActionListDialog dialog;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        dialogBuilder = new MyDialogBuilder();
        menuItemFactories.add(menuItemFactory);
        insertMenu = new InsertMenu(gui, dialogBuilderFactory, menuItemFactories);
    }

    @Test
    public void displayShouldShowDialog() {
        //given
        given(dialogBuilderFactory.create()).willReturn(dialogBuilder);
        given(menuItemFactory.create(dn, insertMenu)).willReturn(Stream.of(menuItem));
        given(menuItem.getLabel()).willReturn("label");
        given(menuItem.getAction()).willReturn(action);
        //when
        insertMenu.onDisplayInsertMenu(DisplayInsertMenuEvent.create(dn));
        //then
        then(dialog).should()
                    .showDialog(gui);
    }

    @Test
    public void displayShouldNoShowWhenNothingToInsert() {
        //given
        given(dialogBuilderFactory.create()).willReturn(dialogBuilder);
        given(menuItemFactory.create(dn, insertMenu)).willReturn(Stream.empty());
        //when
        insertMenu.onDisplayInsertMenu(DisplayInsertMenuEvent.create(dn));
        //then
        then(dialog).should(never())
                    .showDialog(gui);
    }

    @Test
    public void canReceiveWillAcceptCreateMenuItems() {
        assertThat(insertMenu.canReceive(MenuItemType.CREATE)).isTrue();
    }

    @Test
    public void canReceiveWillNotAcceptModifyMenuItems() {
        assertThat(insertMenu.canReceive(MenuItemType.MODIFY)).isFalse();
    }

    @Test
    public void canReceiveWillThrowNPEWhenTypeIsNull() {
        //given
        exception.expect(NullPointerException.class);
        exception.expectMessage("type");
        //when
        insertMenu.canReceive(null);
    }

    private class MyDialogBuilder extends ActionListDialogBuilder {

        @Override
        protected ActionListDialog buildDialog() {
            return dialog;
        }
    }
}
