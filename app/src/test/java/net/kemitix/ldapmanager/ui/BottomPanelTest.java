package net.kemitix.ldapmanager.ui;

import net.kemitix.ldapmanager.handlers.KeyStrokeHandler;
import net.kemitix.ldapmanager.state.KeyStrokeHandlers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * Tests for {@link BottomPanel}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class BottomPanelTest {

    private BottomPanel bottomPanel;

    @Mock
    private KeyStrokeHandlers keyStrokeHandlers;

    private Set<KeyStrokeHandler> handlers;

    @Mock
    private KeyStrokeHandler handler;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        bottomPanel = new BottomPanel(keyStrokeHandlers);
        handlers = new HashSet<>();
    }

    @Test
    public void labelShouldBeInPanel() {
        //when
        bottomPanel.init();
        //then
        assertThat(bottomPanel.getChildCount()).isEqualTo(1);
        assertThat(bottomPanel.getStatusBarLabel()
                              .isInside(bottomPanel));
    }

    @Test
    public void shouldListHandlersInLabel() {
        //given
        handlers.add(handler);
        given(keyStrokeHandlers.getKeyStrokeHandlers()).willReturn(handlers);
        given(handler.isActive()).willReturn(true);
        given(handler.getPrompt()).willReturn(Optional.of("prompt"));
        //when
        bottomPanel.init();
        //then
        assertThat(bottomPanel.getStatusBarLabel().getText()).isEqualTo("prompt");
    }
}
