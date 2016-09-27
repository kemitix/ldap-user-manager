package net.kemitix.ldapmanager.ui;

import com.googlecode.lanterna.gui2.Border;
import com.googlecode.lanterna.gui2.BorderLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link TopPanel}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class TopPanelTest {

    private TopPanel topPanel;

    @Mock
    private Label currentOuLabel;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        topPanel = new TopPanel(currentOuLabel);
    }

    @Test
    public void shouldInit() throws Exception {
        //when
        topPanel.init();
        //then
        assertThat(topPanel.getLayoutData()).isEqualTo(BorderLayout.Location.TOP);
        topPanel.getChildren()
                .forEach(child -> {
                    assertThat(child).isInstanceOf(Border.class);
                    Border border = (Border) child;
                    assertThat(border.getComponent()).isInstanceOf(Panel.class);
                    Panel panel = (Panel) border.getComponent();
                    panel.getChildren()
                         .forEach(innerChild -> assertThat(innerChild).isSameAs(currentOuLabel));
                });
    }
}
