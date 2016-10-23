package net.kemitix.ldapmanager.popupmenus.context;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Bean;

import javax.naming.Name;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link DisplayContextMenuEvent}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class DisplayContextMenuEventTest {

    @Mock
    private Name dn;

    @Bean
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldCreateAndGetDnBackOut() throws Exception {
        assertThat(DisplayContextMenuEvent.of(dn, "title")
                                          .getDn()).isSameAs(dn);
    }

    @Test
    public void shouldCreateAndGetTitleBackOut() throws Exception {
        assertThat(DisplayContextMenuEvent.of(dn, "title")
                                          .getTitle()).isEqualTo("title");
    }
}
