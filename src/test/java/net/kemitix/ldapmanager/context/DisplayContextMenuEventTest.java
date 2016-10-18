package net.kemitix.ldapmanager.context;

import net.kemitix.ldapmanager.navigation.NavigationItem;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Bean;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link DisplayContextMenuEvent}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class DisplayContextMenuEventTest {

    @Mock
    private NavigationItem navigationItem;

    @Bean
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldCreateAndGetNavigationItemBackOut() throws Exception {
        assertThat(DisplayContextMenuEvent.of(navigationItem)
                                          .getNavigationItem()).isSameAs(navigationItem);
    }
}
