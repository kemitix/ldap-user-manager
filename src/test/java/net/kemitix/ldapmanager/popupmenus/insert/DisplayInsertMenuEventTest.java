package net.kemitix.ldapmanager.popupmenus.insert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.naming.Name;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link DisplayInsertMenuEvent}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class DisplayInsertMenuEventTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Mock
    private Name dn;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldCreateAndGetDnBackOut() throws Exception {
        assertThat(DisplayInsertMenuEvent.create(dn)
                                         .getDn()).isSameAs(dn);
    }

    @Test
    public void createShouldThrowNPEWhenDnIsNull() throws Exception {
        //given
        exception.expect(NullPointerException.class);
        exception.expectMessage("dn");
        //when
        DisplayInsertMenuEvent.create(null);
    }
}
