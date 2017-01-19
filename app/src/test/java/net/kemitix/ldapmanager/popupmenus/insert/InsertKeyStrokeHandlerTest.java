package net.kemitix.ldapmanager.popupmenus.insert;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import net.kemitix.ldapmanager.ldap.LdapNameUtil;
import net.kemitix.ldapmanager.state.CurrentContainer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;

/**
 * Tests for {@link InsertKeyStrokeHandler}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class InsertKeyStrokeHandlerTest {

    private InsertKeyStrokeHandler handler;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @Mock
    private CurrentContainer currentContainer;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        handler = new InsertKeyStrokeHandler(applicationEventPublisher, currentContainer);
    }

    @Test
    public void shouldBeActive() {
        assertThat(handler.isActive()).isTrue();
    }

    @Test
    public void shouldHaveInsertDescription() {
        assertThat(handler.getDescription()).isEqualTo("Insert");
    }

    @Test
    public void shouldHaveKeyIns() {
        assertThat(handler.getKey()).isEqualTo("Ins");
    }

    @Test
    public void shouldHandleKeyValid() {
        assertThat(handler.canHandleKey(new KeyStroke(KeyType.Insert))).isTrue();
    }

    @Test
    public void shouldHandleKeyInvalid() {
        assertThat(handler.canHandleKey(new KeyStroke(KeyType.F2))).isFalse();
    }

    @Test
    public void handleKeyShouldPublishEvent() {
        //given
        given(currentContainer.getDn()).willReturn(LdapNameUtil.parse("cn=bob"));
        //when
        handler.handleInput(null);
        //then
        then(applicationEventPublisher).should()
                                       .publishEvent(any(DisplayInsertMenuEvent.class));
    }
}
