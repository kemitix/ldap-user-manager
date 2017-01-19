package net.kemitix.ldapmanager.ui;

import com.googlecode.lanterna.gui2.Label;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link Focusable}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class TopPanelTest {

    private TopPanel topPanel;

    private Label currentOuLabel;

    private Label clockLabel;

    @Before
    public void setUp() throws Exception {
        currentOuLabel = new Label("current ou");
        clockLabel = new Label("clock");
        topPanel = new TopPanel(currentOuLabel, clockLabel);
    }

    @Test
    public void shouldInit() throws Exception {
        //when
        topPanel.init();
        //then
        assertThat(currentOuLabel.isInside(topPanel)).isTrue();
        assertThat(clockLabel.isInside(topPanel)).isTrue();
    }
}
