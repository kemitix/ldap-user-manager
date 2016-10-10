package net.kemitix.ldapmanager.ui;

import net.kemitix.ldapmanager.state.KeyStrokeHandlers;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Tests for {@link BottomPanel}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class BottomPanelTest {

    private BottomPanel bottomPanel;

    @Mock
    private KeyStrokeHandlers keyStrokeHandlers;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        bottomPanel = new BottomPanel(keyStrokeHandlers);
        bottomPanel.init();
    }
}
