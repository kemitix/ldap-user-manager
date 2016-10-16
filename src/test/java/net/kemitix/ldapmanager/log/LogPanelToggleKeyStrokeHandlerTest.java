package net.kemitix.ldapmanager.log;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;

/**
 * Tests for {@link LogPanelToggleKeyStrokeHandler}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class LogPanelToggleKeyStrokeHandlerTest {

    private LogPanelToggleKeyStrokeHandler handler;

    @Mock
    private LogPanel logPanel;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        handler = new LogPanelToggleKeyStrokeHandler(logPanel);
    }

    @Test
    public void shouldIsActive() throws Exception {
        assertThat(handler.isActive()).isTrue();
    }

    @Test
    public void shouldGetDescription() throws Exception {
        assertThat(handler.getDescription());
    }

    @Test
    public void shouldGetKey() throws Exception {
        assertThat(handler.getKey()).isEqualTo("L");
    }

    @Test
    public void shouldCanHandleKeyValid() throws Exception {
        assertThat(handler.canHandleKey(new KeyStroke('L', false, false))).isTrue();
    }

    @Test
    public void shouldCanHandleKeyInValid() throws Exception {
        assertThat(handler.canHandleKey(new KeyStroke('x', false, false))).isFalse();
        assertThat(handler.canHandleKey(new KeyStroke(KeyType.Escape))).isFalse();
    }

    @Test
    public void shouldHandleInput() throws Exception {
        //when
        handler.handleInput(new KeyStroke('L', false, false));
        //then
        then(logPanel).should()
                      .toggleVisibility();
    }
}
