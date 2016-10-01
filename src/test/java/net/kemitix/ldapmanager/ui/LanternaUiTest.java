package net.kemitix.ldapmanager.ui;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.mockito.BDDMockito.then;

/**
 * Tests for {@link LanternaUi}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class LanternaUiTest {

    @InjectMocks
    private LanternaUi ui;

    @Mock
    private BasicWindow mainWindow;

    @Mock
    private WindowBasedTextGUI gui;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void run() throws IOException {
        //when
        ui.run();
        //then
        then(gui).should()
                 .addWindow(mainWindow);
        then(gui).should().waitForWindowToClose(mainWindow);
    }
}
