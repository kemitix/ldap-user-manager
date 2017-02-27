package net.kemitix.ldapmanager.events;

import lombok.val;
import net.kemitix.ldapmanager.handlers.KeyStrokeHandler;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link KeyStrokeHandlerUpdateEvent}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class KeyStrokeHandlerUpdateEventTest {

    @Test
    public void of() throws Exception {
        //given
        val keyStrokeHandler = mock(KeyStrokeHandler.class);
        //when
        val event = KeyStrokeHandlerUpdateEvent.of(keyStrokeHandler);
        //then
        assertThat(event.getKeyStrokeHandler()).isSameAs(keyStrokeHandler);
    }
}
