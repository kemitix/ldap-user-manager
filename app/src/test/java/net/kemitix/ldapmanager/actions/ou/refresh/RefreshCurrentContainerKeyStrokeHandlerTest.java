package net.kemitix.ldapmanager.actions.ou.refresh;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import net.kemitix.ldapmanager.events.ContainerExpiredEvent;
import net.kemitix.ldapmanager.state.CurrentContainer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

import javax.naming.Name;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;

/**
 * Tests for {@link RefreshCurrentContainerKeyStrokeHandler}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class RefreshCurrentContainerKeyStrokeHandlerTest {

    private RefreshCurrentContainerKeyStrokeHandler handler;

    @Mock
    private CurrentContainer currentContainer;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @Mock
    private Name dn;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        handler = new RefreshCurrentContainerKeyStrokeHandler(currentContainer, applicationEventPublisher);
        given(currentContainer.getDn()).willReturn(dn);
    }

    @Test
    public void shouldIsActive() throws Exception {
        assertThat(handler.isActive()).isTrue();
    }

    @Test
    public void shouldGetDescription() throws Exception {
        assertThat(handler.getDescription()).isEqualTo("Refresh");
    }

    @Test
    public void shouldGetKey() throws Exception {
        assertThat(handler.getKey()).isEqualTo("F5");
    }

    @Test
    public void shouldCanHandleKeyValid() throws Exception {
        assertThat(handler.canHandleKey(new KeyStroke(KeyType.F5))).isTrue();
    }

    @Test
    public void shouldCanHandleKeyInValid() throws Exception {
        assertThat(handler.canHandleKey(new KeyStroke('x', false, false))).isFalse();
        assertThat(handler.canHandleKey(new KeyStroke(KeyType.Escape))).isFalse();
    }

    @Test
    public void shouldHandleInput() throws Exception {
        //when
        handler.handleInput(new KeyStroke(KeyType.F5));
        //then
        then(applicationEventPublisher).should()
                                       .publishEvent(any(ContainerExpiredEvent.class));
    }
}
