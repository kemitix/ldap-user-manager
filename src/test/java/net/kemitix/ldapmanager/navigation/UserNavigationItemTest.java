package net.kemitix.ldapmanager.navigation;

import net.kemitix.ldapmanager.domain.User;
import net.kemitix.ldapmanager.navigation.events.NavigationItemUserActionEvent;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;

/**
 * Tests for {@link UserNavigationItem}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class UserNavigationItemTest {

    private NavigationItem userNavigationItem;

    private User user;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    private String name;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        name = "name";
        user = User.builder()
                   .cn(name)
                   .build();
        userNavigationItem = UserNavigationItem.create(user, applicationEventPublisher);
    }

    @Test
    public void create() throws Exception {
        assertThat(userNavigationItem).isInstanceOf(UserNavigationItem.class);
    }

    @Test
    public void run() throws Exception {
        //when
        userNavigationItem.run();
        //then
        then(applicationEventPublisher).should()
                                       .publishEvent(any(NavigationItemUserActionEvent.class));
    }

    @Test
    public void getUser() throws Exception {
        assertThat(((UserNavigationItem) userNavigationItem).getUser()).isSameAs(user);
    }

    @Test
    public void toStringIsName() throws Exception {
        assertThat(userNavigationItem.toString()).isEqualTo(name);
    }
}
