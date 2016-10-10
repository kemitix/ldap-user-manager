package net.kemitix.ldapmanager.events;

import lombok.val;
import net.kemitix.ldapmanager.handlers.KeyStrokeHandler;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link KeyStrokeHandlerUpdateEvent}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class KeyStrokeHandlerUpdateEventTest {

    @Mock
    private KeyStrokeHandler keyStrokeHandler;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldOf() throws Exception {
        //when
        val event = KeyStrokeHandlerUpdateEvent.of(keyStrokeHandler);
        //then
        assertThat(event.getKeyStrokeHandler()).isSameAs(keyStrokeHandler);
    }
}
