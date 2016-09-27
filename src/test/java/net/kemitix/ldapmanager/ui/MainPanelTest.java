package net.kemitix.ldapmanager.ui;

import com.googlecode.lanterna.gui2.Border;
import com.googlecode.lanterna.gui2.BorderLayout;
import com.googlecode.lanterna.gui2.Component;
import com.googlecode.lanterna.gui2.Panel;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link MainPanel}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class MainPanelTest {

    private MainPanel mainPanel;

    @Mock
    private Panel topPanel;

    @Mock
    private Panel bottomPanel;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mainPanel = new MainPanel(topPanel, bottomPanel);
    }

    @Test
    public void shouldInit() throws Exception {
        //when
        mainPanel.init();
        //then
        mainPanel.getChildren()
                 .forEach(child -> {
                     assertThat(child).isInstanceOf(Border.class);
                     final Border border = (Border) child;
                     assertThat(border.getParent()).isSameAs(mainPanel);
                     final Component innerComponent = border.getComponent();
                     assertThat(innerComponent).isInstanceOf(Panel.class);
                     final Panel innerPanel = (Panel) innerComponent;
                     assertThat(innerPanel.getLayoutManager()).isInstanceOf(BorderLayout.class);
                     assertThat(innerPanel.getLayoutData()).isEqualTo(BorderLayout.Location.CENTER);
                     assertThat(innerPanel.getChildren()).containsExactly(topPanel, bottomPanel);

                 });
        assertThat(mainPanel.getLayoutManager()).isInstanceOf(BorderLayout.class);
        assertThat(mainPanel.getLayoutData()).isEqualTo(BorderLayout.Location.CENTER);
    }

}
