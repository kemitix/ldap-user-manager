package net.kemitix.ldapmanager.ui;

import lombok.val;
import net.kemitix.ldapmanager.state.LogMessages;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * Tests for {@link LogPanel}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class LogPanelTest {

    @InjectMocks
    private LogPanel logPanel;

    @Mock
    private LogMessages logMessages;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        logPanel.init();
    }

    @Test
    public void shouldUpdate() throws Exception {
        //given
        val messageLabel = logPanel.getMessageLabel();
        assertThat(messageLabel.getText()).isEmpty();
        given(logMessages.getMessages()).willReturn(Collections.singletonList("redrum"));
        //when
        logPanel.update();
        //then
        assertThat(messageLabel.getText()).isEqualTo("redrum");
    }
}
