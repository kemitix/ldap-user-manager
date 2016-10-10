package net.kemitix.ldapmanager.handlers;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import net.kemitix.ldapmanager.events.ApplicationExitEvent;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;

/**
 * Tests for {@link EscapeKeyStrokeHandler}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class EscapeKeyStrokeHandlerTest {

    private EscapeKeyStrokeHandler handler;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        handler = new EscapeKeyStrokeHandler(eventPublisher);
    }

    @Test
    public void shouldIsActive() throws Exception {
        assertThat(handler.isActive()).isTrue();
    }

    @Test
    public void shouldGetDescription() throws Exception {
        assertThat(handler.getDescription()).containsIgnoringCase("Exit");
    }

    @Test
    public void shouldGetKey() throws Exception {
        assertThat(handler.getKey()).containsIgnoringCase("esc");
    }

    @Test
    public void shouldCanHandleKey() throws Exception {
        assertThat(handler.canHandleKey(new KeyStroke(KeyType.Escape))).isTrue();
        assertThat(handler.canHandleKey(new KeyStroke(KeyType.Enter))).isFalse();
    }

    @Test
    public void shouldHandleInput() throws Exception {
        //when
        handler.handleInput(new KeyStroke(KeyType.Escape));
        //then
        then(eventPublisher).should()
                            .publishEvent(any(ApplicationExitEvent.class));
    }
}
