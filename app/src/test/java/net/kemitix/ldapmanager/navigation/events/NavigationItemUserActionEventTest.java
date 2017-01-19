package net.kemitix.ldapmanager.navigation.events;

import lombok.val;
import net.kemitix.ldapmanager.domain.User;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link NavigationItemUserActionEvent}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class NavigationItemUserActionEventTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

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

    @Test
    public void shouldThrowNPEWhenCreateEventWithNullUser() {
        //given
        exception.expect(NullPointerException.class);
        exception.expectMessage("user");
        //when
        NavigationItemUserActionEvent.of(null);
    }
}
