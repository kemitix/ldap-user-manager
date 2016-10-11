package net.kemitix.ldapmanager.state;

import com.googlecode.lanterna.input.KeyStroke;
import lombok.val;
import net.kemitix.ldapmanager.handlers.KeyStrokeHandler;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

/**
 * Tests for {@link DefaultKeyStrokeHandlers}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class DefaultKeyStrokeHandlersTest {

    private DefaultKeyStrokeHandlers keyStrokeHandlers;

    @Mock
    private KeyStrokeHandler keyStrokeHandler;

    @Mock
    private KeyStroke keyStroke;

    private Set<KeyStrokeHandler> handlers = new HashSet<>();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        handlers.add(keyStrokeHandler);
        keyStrokeHandlers = new DefaultKeyStrokeHandlers(handlers);
    }

    @Test
    public void shouldGetKeyStrokeHandlers() throws Exception {
        assertThat(keyStrokeHandlers.getKeyStrokeHandlers()).containsOnly(keyStrokeHandler);
    }

    @Test
    public void shouldHandleInputWhenActiveAndCanHandle() throws Exception {
        //given
        handlerIsActive(true);
        handleCanHandle(true);
        //when
        val handled = keyStrokeHandlers.handleInput(keyStroke);
        //then
        then(keyStrokeHandler).should()
                              .handleInput(keyStroke);
        assertThat(handled).isTrue();
    }

    @Test
    public void shouldHandleInputWhenActiveAndCanNotHandle() throws Exception {
        //given
        handlerIsActive(true);
        handleCanHandle(false);
        //when
        val handled = keyStrokeHandlers.handleInput(keyStroke);
        //then
        then(keyStrokeHandler).should(never())
                              .handleInput(keyStroke);
        assertThat(handled).isFalse();
    }

    @Test
    public void shouldHandleInputWhenNotActiveAndCanHandle() throws Exception {
        //given
        handlerIsActive(false);
        handleCanHandle(true);
        //when
        val handled = keyStrokeHandlers.handleInput(keyStroke);
        //then
        then(keyStrokeHandler).should(never())
                              .handleInput(keyStroke);
        assertThat(handled).isFalse();
    }

    @Test
    public void shouldHandleInputWhenNotActiveAndCanNotHandle() throws Exception {
        //given
        handlerIsActive(false);
        handleCanHandle(false);
        //when
        val handled = keyStrokeHandlers.handleInput(keyStroke);
        //then
        then(keyStrokeHandler).should(never())
                              .handleInput(keyStroke);
        assertThat(handled).isFalse();
    }

    private void handleCanHandle(final boolean state) {
        given(keyStrokeHandler.canHandleKey(keyStroke)).willReturn(state);
    }

    private void handlerIsActive(final boolean state) {
        given(keyStrokeHandler.isActive()).willReturn(state);
    }
}
