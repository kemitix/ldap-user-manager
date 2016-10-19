package net.kemitix.ldapmanager.actions.user.password;

import net.kemitix.ldapmanager.navigation.NavigationItem;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;

/**
 * Tests for {@link ChangePasswordMenuItem}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class ChangePasswordMenuItemTest {

    @Mock
    private NavigationItem navigationItem;

    private ChangePasswordMenuItem menuItem;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        menuItem = new ChangePasswordMenuItem(navigationItem);
    }

    @Test
    public void shouldGetLabel() throws Exception {
        assertThat(menuItem.getLabel()).isSameAs("Change Password");
    }

    @Test
    public void shouldGetAction() throws Exception {
        //when
        menuItem.getAction()
                .run();
        //then
        then(navigationItem).should()
                            .publishChangePasswordRequest();
    }
}
