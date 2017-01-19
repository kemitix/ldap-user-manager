package net.kemitix.ldapmanager.actions.user.create;

import lombok.val;
import net.kemitix.ldapmanager.popupmenus.MenuItemReceiver;
import net.kemitix.ldapmanager.popupmenus.MenuItemType;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

import javax.naming.Name;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * Tests for {@link CreateUserMenuItemFactory}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class CreateUserMenuItemFactoryTest {

    private CreateUserMenuItemFactory menuItemFactory;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @Mock
    private Name dn;

    @Mock
    private MenuItemReceiver menuItemReceiver;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        menuItemFactory = new CreateUserMenuItemFactory(applicationEventPublisher);
    }

    @Test
    public void shouldCreateForCreateMenu() throws Exception {
        //given
        given(menuItemReceiver.canReceive(MenuItemType.CREATE)).willReturn(true);
        //when
        val menuItems = menuItemFactory.create(dn, menuItemReceiver)
                                       .collect(Collectors.toList());
        //then
        assertThat(menuItems).hasSize(1);
        val menuItem = menuItems.get(0);
        assertThat(menuItem).isInstanceOf(CreateUserMenuItem.class);
    }

    @Test
    public void shouldCreateForModifyMenu() throws Exception {
        //given
        given(menuItemReceiver.canReceive(MenuItemType.MODIFY)).willReturn(true);
        //when
        val menuItems = menuItemFactory.create(dn, menuItemReceiver)
                                       .collect(Collectors.toList());
        //then
        assertThat(menuItems).isEmpty();
    }
}
