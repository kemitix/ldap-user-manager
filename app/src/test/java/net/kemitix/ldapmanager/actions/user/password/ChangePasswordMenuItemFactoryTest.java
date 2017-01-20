package net.kemitix.ldapmanager.actions.user.password;

import lombok.val;
import net.kemitix.ldapmanager.domain.Features;
import net.kemitix.ldapmanager.domain.LdapEntity;
import net.kemitix.ldapmanager.events.ChangePasswordRequestEvent;
import net.kemitix.ldapmanager.ldap.LdapNameUtil;
import net.kemitix.ldapmanager.ldap.NameLookupService;
import net.kemitix.ldapmanager.popupmenus.MenuItemReceiver;
import net.kemitix.ldapmanager.popupmenus.MenuItemType;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;

/**
 * .
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class ChangePasswordMenuItemFactoryTest {

    private ChangePasswordMenuItemFactory factory;

    @Mock
    private NameLookupService nameLookupService;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @Mock
    private LdapEntity ldapEntity;

    @Mock
    private MenuItemReceiver menu;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        factory = new ChangePasswordMenuItemFactory(nameLookupService, applicationEventPublisher);
        given(menu.canReceive(MenuItemType.MODIFY)).willReturn(true);
    }

    @Test
    public void whenNavigationItemHasPasswordFeatureThenPublishChangeRequest() throws Exception {
        //given
        given(ldapEntity.hasFeature(Features.PASSWORD)).willReturn(true);
        val dn = LdapNameUtil.parse("dn=user");
        given(nameLookupService.findByDn(dn)).willReturn(Optional.of(ldapEntity));
        //when
        factory.create(dn, menu)
               .forEach(menuItem -> menuItem.getAction()
                                            .run());
        //then
        then(applicationEventPublisher).should()
                                       .publishEvent(any(ChangePasswordRequestEvent.class));
    }

    @Test
    public void whenNavigationItemHasNoPasswordFeatureThenDoNotCreateMenuItem() throws Exception {
        //given
        given(ldapEntity.hasFeature(Features.PASSWORD)).willReturn(false);
        val dn = LdapNameUtil.parse("dn=user");
        given(nameLookupService.findByDn(dn)).willReturn(Optional.of(ldapEntity));
        //then
        assertThat(factory.create(dn, menu)).isEmpty();
    }

    @Test
    public void whenMenuWillNotAcceptMenuItemThenDoNotCreateMenuItem() throws Exception {
        //given
        given(menu.canReceive(any())).willReturn(false);
        //then
        assertThat(factory.create(LdapNameUtil.parse("dn=user"), menu)).isEmpty();
    }
}
