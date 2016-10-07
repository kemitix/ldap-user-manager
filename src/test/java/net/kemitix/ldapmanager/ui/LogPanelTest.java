package net.kemitix.ldapmanager.ui;

import com.googlecode.lanterna.gui2.Label;
import lombok.val;
import net.kemitix.ldapmanager.state.LogMessages;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;

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
        logPanel.init();
        messageLabel = logPanel.getMessageLabel();
    }

    @Test
    public void shouldUpdateWhenLessThanLinesToShow() throws Exception {
        //given
        assertThat(messageLabel.getText()).isEmpty();
        given(logMessages.getMessages()).willReturn(Collections.singletonList("redrum"));
        //when
        logPanel.update();
        //then
        assertThat(messageLabel.getText()
                               .split("\n")).containsExactly("", "redrum");
    }

    @Test
    public void shouldUpdateWhenEqualsLinesToShow() throws Exception {
        //given
        given(logMessages.getMessages()).willReturn(Arrays.asList("line 1", "line 2"));
        //when
        logPanel.update();
        //then
        assertThat(messageLabel.getText()
                               .split("\n")).containsExactly("line 1", "line 2");
    }

    @Test
    public void shouldUpdateWhenMoreThanLinesToShow() throws Exception {
        //given
        given(logMessages.getMessages()).willReturn(Arrays.asList("line 1", "line 2", "line 3"));
        //when
        logPanel.update();
        //then
        assertThat(messageLabel.getText()
                               .split("\n")).containsExactly("line 2", "line 3");
    }
}
