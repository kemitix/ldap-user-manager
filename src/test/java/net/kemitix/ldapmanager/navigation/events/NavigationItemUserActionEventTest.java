package net.kemitix.ldapmanager.navigation.events;

import lombok.val;
import net.kemitix.ldapmanager.domain.User;
import net.kemitix.ldapmanager.navigation.events.NavigationItemUserActionEvent;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link NavigationItemUserActionEvent}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class NavigationItemUserActionEventTest {

    @Test
    public void shouldCreateEventAndGetUserBackOut() throws Exception {
        //given
        val user = User.builder()
                       .build();
        //when
        val event = NavigationItemUserActionEvent.of(user);
        //then
        assertThat(event.getUser()).isSameAs(user);
    }
}
