package net.kemitix.ldapmanager.actions.user.password;

import net.kemitix.ldapmanager.navigation.NavigationItem;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
    public void shouldCreateWithActionThatPublishedOnNavigationItem() throws Exception {
        //when
        factory.create(navigationItem)
               .forEach(menuItem -> menuItem.getAction()
                                            .run());
        //then
        then(navigationItem).should()
                            .publishChangePasswordRequest();
    }
}
