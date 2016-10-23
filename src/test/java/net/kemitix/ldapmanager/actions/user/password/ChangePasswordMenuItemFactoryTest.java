package net.kemitix.ldapmanager.actions.user.password;

import net.kemitix.ldapmanager.domain.Features;
import net.kemitix.ldapmanager.navigation.NavigationItem;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

/**
 * .
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class ChangePasswordMenuItemFactoryTest {

    private ChangePasswordMenuItemFactory factory;

    @Mock
    private NavigationItem navigationItem;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        factory = new ChangePasswordMenuItemFactory();
    }

    @Test
    public void whenNavigationItemHasPasswordFeatureThenPublishChangeRequest() throws Exception {
        //given
        given(navigationItem.hasFeature(Features.PASSWORD)).willReturn(true);
        //when
        factory.create(navigationItem)
               .forEach(menuItem -> menuItem.getAction()
                                            .run());
        //then
        then(navigationItem).should()
                            .publishChangePasswordRequest();
    }

    @Test
    public void whenNavigationItemHasNoPasswordFeatureThenDoNotPublishChangeRequest() throws Exception {
        //given
        given(navigationItem.hasFeature(Features.PASSWORD)).willReturn(false);
        //when
        factory.create(navigationItem)
               .forEach(menuItem -> menuItem.getAction()
                                            .run());
        //then
        then(navigationItem).should(never())
                            .publishChangePasswordRequest();
    }
}
