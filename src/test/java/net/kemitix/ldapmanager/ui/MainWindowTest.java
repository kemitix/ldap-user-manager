package net.kemitix.ldapmanager.ui;

import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link MainWindow}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class MainWindowTest {

    private MainWindow mainWindow;

    @Mock
    private Panel mainPanel;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mainWindow = new MainWindow(mainPanel);
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

}
