package net.kemitix.ldapmanager.popupmenus.context;

import net.kemitix.ldapmanager.ldap.LdapNameUtil;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.naming.Name;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link DisplayContextMenuEvent}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class DisplayContextMenuEventTest {

    private DisplayContextMenuEvent event;

    @Mock
    private Name dn;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        event = DisplayContextMenuEvent.of(dn, "title");
    }

    @Test
    public void shouldCreateAndGetDnBackOut() throws Exception {
        assertThat(event.getDn()).isSameAs(dn);
    }

    @Test
    public void shouldCreateAndGetTitleBackOut() throws Exception {
        assertThat(event.getTitle()).isEqualTo("title");
    }

    @Test
    public void shouldThrowNPEWhenDnIsNull() throws Exception {
        //given
        exception.expect(NullPointerException.class);
        exception.expectMessage("dn");
        //when
        DisplayContextMenuEvent.of(null, "title");
    }

    @Test
    public void shouldThrowNPEWhenTitleIsNull() throws Exception {
        //given
        exception.expect(NullPointerException.class);
        exception.expectMessage("title");
        //when
        DisplayContextMenuEvent.of(LdapNameUtil.empty(), null);
    }
}
