package net.kemitix.ldapmanager.handlers;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import lombok.val;
import net.kemitix.ldapmanager.events.ApplicationExitEvent;
import net.kemitix.ldapmanager.events.ApplicationExitRequestEvent;
import net.kemitix.ldapmanager.events.DeniableRequestEvent;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;

/**
 * Tests for {@link EscapeKeyStrokeHandler}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class EscapeKeyStrokeHandlerTest {

    private EscapeKeyStrokeHandler handler;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Captor
    private ArgumentCaptor<Object> objectArgumentCaptor;

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
        then(eventPublisher).should(times(2))
                            .publishEvent(objectArgumentCaptor.capture());
        val events = objectArgumentCaptor.getAllValues();
        assertThat(events).hasSize(2);
        assertThat(events.get(0)).isInstanceOf(ApplicationExitRequestEvent.class);
        val requestEvent = (ApplicationExitRequestEvent) events.get(0);
        assertThat(requestEvent.isApproved()).isTrue();
        assertThat(events.get(1)).isInstanceOf(ApplicationExitEvent.class);
    }

    @Test
    public void shouldHandleEventWhenExitDenied() throws Exception {
        //given
        doAnswer(invocation -> {
            ((DeniableRequestEvent) invocation.getArguments()[0]).deny();
            return null;
        }).when(eventPublisher)
          .publishEvent(any(ApplicationExitRequestEvent.class));
        //when
        handler.handleInput(new KeyStroke(KeyType.Escape));
        //then
        then(eventPublisher).should(times(1))
                            .publishEvent(objectArgumentCaptor.capture());
        val events = objectArgumentCaptor.getAllValues();
        assertThat(events).hasSize(1);
        assertThat(events.get(0)).isInstanceOf(ApplicationExitRequestEvent.class);
        val requestEvent = (ApplicationExitRequestEvent) events.get(0);
        assertThat(requestEvent.isApproved()).isFalse();
    }

    @Test
    public void shouldGetPrompt() throws Exception {
        assertThat(handler.getPrompt()).contains("[esc] Exit");
    }
}
