package net.kemitix.ldapmanager.ui;

import com.googlecode.lanterna.gui2.BasePaneListener;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import lombok.val;
import net.kemitix.ldapmanager.state.KeyStrokeHandlers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;

/**
 * Tests for {@link MainWindow}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class MainWindowTest {

    private TestMainWindow mainWindow;

    @Mock
    private Panel mainPanel;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Mock
    private KeyStrokeHandlers keyStrokeHandlers;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mainWindow = spy(new TestMainWindow(mainPanel, eventPublisher, keyStrokeHandlers));
    }

    @Test
    public void shouldInit() throws Exception {
        //when
        mainWindow.init();
        //then
        assertThat(mainWindow.getHints()).containsExactlyInAnyOrder(
                Window.Hint.FULL_SCREEN, Window.Hint.NO_DECORATIONS);
        assertThat(mainWindow.getComponent()).isSameAs(mainPanel);
    }

    @Test
    public void listenerShouldCloseWindow() throws IOException {
        //when
        mainWindow.onApplicationExit();
        //then
        then(mainWindow).should()
                        .close();
    }

    @Test
    public void pressingEscapeIsPassedToKeyStrokeHandlers() throws Exception {
        //given
        val keyStroke = new KeyStroke(KeyType.Escape);
        //when
        mainWindow.handleInput(keyStroke);
        //then
        then(keyStrokeHandlers).should()
                               .handleInput(eq(keyStroke));
    }

    @Test
    public void handleInputShouldReturnTrueWhenHandledByFramework() throws Exception {
        //given
        val keyStroke = new KeyStroke(KeyType.Enter);
        val basePaneListener = new BaseListener(true);
        mainWindow.addBasePaneListener(basePaneListener);
        //when
        val result = mainWindow.handleInput(keyStroke);
        //then
        assertThat(result).isTrue();
        then(keyStrokeHandlers).should(never())
                               .handleInput(any());
    }

    @Test
    public void handleInputShouldReturnTrueWhenHandledByKeyStrokeHandler() throws Exception {
        //given
        val keyStroke = new KeyStroke(KeyType.Enter);
        val basePaneListener = new BaseListener(false);
        mainWindow.addBasePaneListener(basePaneListener);
        given(keyStrokeHandlers.handleInput(keyStroke)).willReturn(true);
        //when
        val result = mainWindow.handleInput(keyStroke);
        //then
        assertThat(result).isTrue();
    }

    @Test
    public void handleInputShouldReturnFalseWhenNotHandled() throws Exception {
        //given
        val keyStroke = new KeyStroke(KeyType.Enter);
        val basePaneListener = new BaseListener(false);
        mainWindow.addBasePaneListener(basePaneListener);
        given(keyStrokeHandlers.handleInput(keyStroke)).willReturn(false);
        //when
        val result = mainWindow.handleInput(keyStroke);
        //then
        assertThat(result).isFalse();
    }

    private static class TestMainWindow extends MainWindow {

        TestMainWindow(
                final Panel mainPanel, final ApplicationEventPublisher eventPublisher,
                final KeyStrokeHandlers keyStrokeHandlers
                      ) {
            super(mainPanel, eventPublisher, keyStrokeHandlers);
        }

        @Override
        public void addBasePaneListener(final BasePaneListener basePaneListener) {
            super.addBasePaneListener(basePaneListener);
        }
    }

    private static class BaseListener implements BasePaneListener<TestMainWindow> {

        private final boolean shouldHandle;

        private BaseListener(final boolean shouldHandle) {
            this.shouldHandle = shouldHandle;
        }

        @Override
        public void onInput(
                final TestMainWindow basePane, final KeyStroke keyStroke, final AtomicBoolean deliverEvent
                           ) {
            deliverEvent.getAndSet(true);
        }

        @Override
        public void onUnhandledInput(
                final TestMainWindow basePane, final KeyStroke keyStroke, final AtomicBoolean hasBeenHandled
                                    ) {
            hasBeenHandled.getAndSet(shouldHandle);
        }
    }
}
