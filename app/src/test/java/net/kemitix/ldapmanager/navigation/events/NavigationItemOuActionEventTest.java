package net.kemitix.ldapmanager.navigation.events;

import lombok.val;
import net.kemitix.ldapmanager.domain.OU;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link NavigationItemOuActionEvent}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class NavigationItemOuActionEventTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

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

    @Test
    public void shouldThrowNPEWhenCreateEventWithNullOu() {
        //given
        exception.expect(NullPointerException.class);
        exception.expectMessage("ou");
        //when
        NavigationItemOuActionEvent.of(null);
    }
}
