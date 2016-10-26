package net.kemitix.ldapmanager.actions.rename;

import lombok.val;
import net.kemitix.ldapmanager.domain.Features;
import net.kemitix.ldapmanager.domain.LdapEntity;
import net.kemitix.ldapmanager.ldap.LdapNameUtil;
import net.kemitix.ldapmanager.ldap.NameLookupService;
import net.kemitix.ldapmanager.navigation.NavigationItem;
import net.kemitix.ldapmanager.popupmenus.MenuItem;
import net.kemitix.ldapmanager.popupmenus.MenuItemReceiver;
import net.kemitix.ldapmanager.popupmenus.MenuItemType;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;

/**
 * Tests for {@link RenameMenuItemFactory}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class RenameMenuItemFactoryTest {

    private RenameMenuItemFactory menuItemFactory;

    @Mock
    private NavigationItem navigationItem;

    @Mock
    private NameLookupService nameLookupService;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @Mock
    private LdapEntity ldapEntity;

    @Mock
    private MenuItemReceiver menu;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        menuItemFactory = new RenameMenuItemFactory(nameLookupService, applicationEventPublisher);
        given(menu.canReceive(MenuItemType.MODIFY)).willReturn(true);
    }

    @Test
    public void labelIsRename() throws Exception {
        //given
        given(ldapEntity.hasFeature(Features.RENAME)).willReturn(true);
        val dn = LdapNameUtil.parse("dn=user");
        given(nameLookupService.findByDn(dn)).willReturn(Optional.of(ldapEntity));
        //when
        final List<MenuItem> menuItems = menuItemFactory.create(dn, menu)
                                                        .collect(Collectors.toList());
        //then
        assertThat(menuItems).hasSize(1);
        assertThat(menuItems.get(0)
                            .getLabel()).isEqualTo("Rename");
    }

    @Test
    public void runActionShouldPublishRenameRequest() throws Exception {
        //given
        given(ldapEntity.hasFeature(Features.RENAME)).willReturn(true);
        val dn = LdapNameUtil.parse("dn=user");
        given(nameLookupService.findByDn(dn)).willReturn(Optional.of(ldapEntity));
        //when
        final List<MenuItem> menuItems = menuItemFactory.create(dn, menu)
                                                        .collect(Collectors.toList());
        //then
        assertThat(menuItems).hasSize(1);
        menuItems.get(0)
                 .getAction()
                 .run();
        then(applicationEventPublisher).should()
                                       .publishEvent(any(RenameRequestEvent.class));
    }

    @Test
    public void whenMenuWillNotAcceptMenuItemThenDoNotCreateMenuItem() throws Exception {
        //given
        given(menu.canReceive(any())).willReturn(false);
        //then
        assertThat(menuItemFactory.create(LdapNameUtil.parse("dn=user"), menu)).isEmpty();
    }
}
