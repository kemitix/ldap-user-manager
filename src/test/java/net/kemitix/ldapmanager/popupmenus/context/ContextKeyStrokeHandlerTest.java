package net.kemitix.ldapmanager.popupmenus.context;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import net.kemitix.ldapmanager.navigation.NavigationItem;
import net.kemitix.ldapmanager.navigation.ui.NavigationItemActionListBox;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

/**
 * Tests for {@link ContextKeyStrokeHandler}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class ContextKeyStrokeHandlerTest {

    private ContextKeyStrokeHandler handler;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @Mock
    private NavigationItemActionListBox navigationItemActionListBox;

    @Mock
    private NavigationItem navigationItem;

    @Captor
    private ArgumentCaptor<DisplayContextMenuEvent> displayContextMenuEventArgumentCaptor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        handler = new ContextKeyStrokeHandler(applicationEventPublisher, navigationItemActionListBox);
    }

    @Test
    public void shouldGetDescription() throws Exception {
        assertThat(handler.getDescription()).isEqualTo("Menu");
    }

    @Test
    public void shouldGetKey() throws Exception {
        assertThat(handler.getKey()).isEqualTo("F2");
    }

    @Test
    public void shouldCanHandleKeyValid() throws Exception {
        assertThat(handler.canHandleKey(new KeyStroke(KeyType.F2))).isTrue();
    }

    @Test
    public void shouldCanHandleKeyInValid() throws Exception {
        assertThat(handler.canHandleKey(new KeyStroke('x', false, false))).isFalse();
        assertThat(handler.canHandleKey(new KeyStroke(KeyType.Escape))).isFalse();
    }

    @Test
    public void shouldHandleInput() throws Exception {
        //given
        given(navigationItemActionListBox.getSelectedItem()).willReturn(navigationItem);
        //when
        handler.handleInput(new KeyStroke(KeyType.F2));
        //then
        then(applicationEventPublisher).should()
                                       .publishEvent(displayContextMenuEventArgumentCaptor.capture());
        assertThat(displayContextMenuEventArgumentCaptor.getValue()
                                                        .getNavigationItem()).isSameAs(navigationItem);
    }
}
