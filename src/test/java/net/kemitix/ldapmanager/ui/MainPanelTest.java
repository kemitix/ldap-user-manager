package net.kemitix.ldapmanager.ui;

import com.googlecode.lanterna.gui2.BorderLayout;
import com.googlecode.lanterna.gui2.Panel;
import lombok.NoArgsConstructor;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.spy;

/**
 * Tests for {@link MainPanel}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class MainPanelTest {

    private MainPanel mainPanel;

    private Panel topPanel;

    private Panel bottomPanel;

    private MainPanelTest.FocusablePanel navigationPanel;

    private Panel logPanel;

    private AbstractFocusablePanel centerPanel;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        topPanel = new Panel();
        bottomPanel = new Panel();
        navigationPanel = spy(new MainPanelTest.FocusablePanel());
        logPanel = new Panel();
        centerPanel = spy(new MainPanelTest.FocusablePanel());
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
        assertThat(centerPanel.isInside(mainPanel)).isTrue();
        assertThat(logPanel.isInside(mainPanel)).isTrue();
        assertThat(mainPanel.getLayoutManager()).isInstanceOf(BorderLayout.class);
        assertThat(mainPanel.getLayoutData()).isEqualTo(BorderLayout.Location.CENTER);
    }

    @Test
    public void shouldOnCloseCentreFor() throws Exception {
        //given
        //when
        mainPanel.onCloseCenterForm();
        //then
        then(navigationPanel).should()
                             .setFocused();
        then(centerPanel).should()
                         .removeAllComponents();
    }

    @NoArgsConstructor
    private static class FocusablePanel extends AbstractFocusablePanel {

        @Override
        public void setFocused() {

        }
    }
}
