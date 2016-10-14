package net.kemitix.ldapmanager.ui;

import net.kemitix.ldapmanager.navigation.ui.NavigationItemActionListBox;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;

/**
 * Tests for {@link NavigationPanel}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class NavigationPanelTest {

    private NavigationPanel navigationPanel;

    @Mock
    private NavigationItemActionListBox actionListBox;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        navigationPanel = new NavigationPanel(actionListBox);
    }

    @Test
    public void shouldInit() {
        //when
        navigationPanel.init();
        //then
        then(actionListBox).should()
                           .setLayoutData(any());
    }
}
