package net.kemitix.ldapmanager.navigation.events;

import lombok.val;
import net.kemitix.ldapmanager.domain.OU;
import net.kemitix.ldapmanager.events.NavigationItemOuActionEvent;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link NavigationItemOuActionEvent}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class NavigationItemOuActionEventTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Mock
    private OU ou;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldCreateEventAndGetOuBackOut() {
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
