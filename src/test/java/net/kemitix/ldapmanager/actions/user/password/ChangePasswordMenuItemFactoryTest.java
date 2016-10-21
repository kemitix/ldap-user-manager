package net.kemitix.ldapmanager.actions.user.password;

import net.kemitix.ldapmanager.domain.Features;
import net.kemitix.ldapmanager.navigation.NavigationItem;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

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
    public void whenNavigationItemHasNoPasswordFeatureThenDoNotCreateMenuItem() throws Exception {
        //given
        given(navigationItem.hasFeature(Features.PASSWORD)).willReturn(false);
        //then
        assertThat(factory.create(navigationItem)).isEmpty();
    }
}
