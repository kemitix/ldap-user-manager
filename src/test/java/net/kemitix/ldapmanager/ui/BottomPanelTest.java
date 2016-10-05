package net.kemitix.ldapmanager.ui;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import lombok.val;
import net.kemitix.ldapmanager.events.ApplicationExitEvent;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;

/**
 * Tests for {@link BottomPanel}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class BottomPanelTest {

    private BottomPanel bottomPanel;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        bottomPanel = new BottomPanel(eventPublisher);
        bottomPanel.init();
    }

    @Test
    public void shouldInit() throws Exception {
        assertThat(bottomPanel.getExitButton()
                              .isInside(bottomPanel)).isTrue();
    }

    @Test
    public void shouldSendApplicationExitEvent() {
        //given
        val exitButton = bottomPanel.getExitButton();
        //when
        exitButton.handleKeyStroke(new KeyStroke(KeyType.Enter));
        //then
        then(eventPublisher).should()
                            .publishEvent(any(ApplicationExitEvent.class));
    }
}
