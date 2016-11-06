package net.kemitix.ldapmanager.ui;

import com.googlecode.lanterna.gui2.BorderLayout;
import com.googlecode.lanterna.gui2.Panel;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link MainPanel}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class MainPanelTest {

    private MainPanel mainPanel;


    private Panel topPanel = new Panel();

    private Panel bottomPanel = new Panel();

    private Panel navigationPanel = new Panel();

    private Panel logPanel = new Panel();

    private Panel centerPanel = new Panel();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mainPanel = new MainPanel(topPanel, bottomPanel, navigationPanel, logPanel, centerPanel);
    }

    @Test
    public void shouldInit() throws Exception {
        //when
        mainPanel.init();
        //then
        assertThat(topPanel.isInside(mainPanel)).isTrue();
        assertThat(bottomPanel.isInside(mainPanel)).isTrue();
        assertThat(navigationPanel.isInside(mainPanel)).isTrue();
        assertThat(logPanel.isInside(mainPanel)).isTrue();
        assertThat(mainPanel.getLayoutManager()).isInstanceOf(BorderLayout.class);
        assertThat(mainPanel.getLayoutData()).isEqualTo(BorderLayout.Location.CENTER);
    }

}
