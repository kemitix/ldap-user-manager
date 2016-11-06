package net.kemitix.ldapmanager.ui;

import com.googlecode.lanterna.gui2.Component;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;

/**
 * Tests for {@link CenterPanel}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class CenterPanelTest {

    private CenterPanel centerPanel;

    @Mock
    private Component component;

    @Mock
    private AbstractFocusablePanel panel;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        centerPanel = new CenterPanel();
        centerPanel.init();
    }

    @Test
    public void shouldReplaceComponents() throws Exception {
        //when
        centerPanel.replaceComponents(component);
        //then
        assertThat(centerPanel.getChildren()).containsOnly(component);
    }

    @Test
    public void shouldSetFocused() throws Exception {
        //given
        centerPanel.replaceComponents(panel);
        //when
        centerPanel.setFocused();
        //then
        then(panel).should()
                   .setFocused();
    }
}
