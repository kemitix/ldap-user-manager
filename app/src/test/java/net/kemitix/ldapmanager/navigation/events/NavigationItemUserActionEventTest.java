package net.kemitix.ldapmanager.navigation.events;

import lombok.val;
import net.kemitix.ldapmanager.domain.User;
import net.kemitix.ldapmanager.events.NavigationItemUserActionEvent;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link NavigationItemUserActionEvent}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class NavigationItemUserActionEventTest {

    @Test
    public void of() throws Exception {
        //given
        val user = mock(User.class);
        //when
        val event = NavigationItemUserActionEvent.of(user);
        //then
        assertThat(event.getUser()).isSameAs(user);
    }
}
