package net.kemitix.ldapmanager.navigation.events;

import lombok.val;
import net.kemitix.ldapmanager.domain.OU;
import net.kemitix.ldapmanager.navigation.events.NavigationItemOuActionEvent;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link NavigationItemOuActionEvent}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class NavigationItemOuActionEventTest {

    @Test
    public void shouldCreateEventAndGetOuBackOut() {
        //given
        val ou = OU.builder()
                   .build();
        //when
        val event = NavigationItemOuActionEvent.of(ou);
        //then
        assertThat(event.getOu()).isSameAs(ou);
    }
}
