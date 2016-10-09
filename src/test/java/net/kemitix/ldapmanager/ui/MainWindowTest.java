package net.kemitix.ldapmanager.ui;

import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import net.kemitix.ldapmanager.events.ApplicationExitEvent;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.spy;

/**
 * Tests for {@link MainWindow}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class MainWindowTest {

    private MainWindow mainWindow;

    @Mock
    private Panel mainPanel;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mainWindow = spy(new MainWindow(mainPanel, eventPublisher));
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
    public void pressingEscapeWillExitTheApplication() throws Exception {
        //when
        mainWindow.handleInput(new KeyStroke(KeyType.Escape));
        //then
        then(eventPublisher).should()
                            .publishEvent(any(ApplicationExitEvent.class));
    }
}
