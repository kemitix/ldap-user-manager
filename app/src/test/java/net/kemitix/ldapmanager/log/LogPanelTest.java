package net.kemitix.ldapmanager.log;

import com.googlecode.lanterna.gui2.Label;
import lombok.val;
import net.kemitix.ldapmanager.state.LogMessages;
import net.kemitix.ldapmanager.ui.UiProperties;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * Tests for {@link LogPanel}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class LogPanelTest {

    private LogPanel logPanel;

    @Mock
    private LogMessages logMessages;

    private Label messageLabel;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        val uiProperties = new UiProperties();
        uiProperties.setLogLinesToShow(2);
        logPanel = new LogPanel(logMessages, uiProperties);
        messageLabel = logPanel.getMessageLabel();
    }

    @Test
    public void shouldUpdateWhenLessThanLinesToShow() throws Exception {
        //given
        assertThat(messageLabel.getText()).isEmpty();
        givenLogMessages(new String[]{"redrum"});
        //when
        logPanel.init();
        logPanel.show();
        //then
        assertThat(messageLabel.getText()
                               .split("\n")).containsExactly("", "redrum");
    }

    @Test
    public void shouldUpdateWhenEqualsLinesToShow() throws Exception {
        //given
        givenLogMessages(new String[]{"line 1", "line 2"});
        //when
        logPanel.init();
        logPanel.show();
        //then
        assertThat(messageLabel.getText()
                               .split("\n")).containsExactly("line 1", "line 2");
    }

    @Test
    public void shouldUpdateWhenMoreThanLinesToShow() throws Exception {
        //given
        givenLogMessages(new String[]{"line 1", "line 2", "line 3"});
        //when
        logPanel.init();
        logPanel.show();
        //then
        assertThat(messageLabel.getText()
                               .split("\n")).containsExactly("line 2", "line 3");
    }

    @Test
    public void shouldNotShowLabelBeDefault() {
        //given
        //when
        logPanel.init();
        //then
        assertThat(logPanel.isVisible()).isFalse();
        assertThat(logPanel.getLabel()
                           .isInside(logPanel)).isFalse();
    }

    @Test
    public void shouldMakeLabelVisible() {
        //given
        givenLogMessages(new String[]{"line 1"});
        logPanel.init();
        //when
        logPanel.show();
        //then
        assertThat(logPanel.isVisible()).isTrue();
        assertThat(logPanel.getLabel()
                           .isInside(logPanel)).isTrue();
    }

    @Test
    public void shouldMakeLabelNotVisible() {
        //given
        givenLogMessages(new String[]{"line 1"});
        logPanel.init();
        logPanel.show();
        //when
        logPanel.hide();
        //then
        assertThat(logPanel.isVisible()).isFalse();
        assertThat(logPanel.getLabel()
                           .isInside(logPanel)).isFalse();
    }


    @Test
    public void shouldToggleLabelVisibility() {
        //given
        givenLogMessages(new String[]{"line 1"});
        logPanel.init();
        //when
        logPanel.toggleVisibility();
        //then
        assertThat(logPanel.isVisible()).as("toggle to visible")
                                        .isTrue();
        assertThat(logPanel.getLabel()
                           .isInside(logPanel)).as("toggle to in panel")
                                               .isTrue();
        //when
        logPanel.toggleVisibility();
        //then
        assertThat(logPanel.isVisible()).as("toggle to not visible")
                                        .isFalse();
        assertThat(logPanel.getLabel()
                           .isInside(logPanel)).as("toggle to not in panel")
                                               .isFalse();
    }

    private void givenLogMessages(final String[] messages) {
        given(logMessages.getMessageCount()).willReturn(messages.length);
        given(logMessages.getMessages()).willReturn(Stream.of(messages));
    }
}
