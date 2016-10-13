package net.kemitix.ldapmanager.navigation;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link NavigationItemSelectionChangedEvent}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class NavigationItemSelectionChangedEventTest {

    private NavigationItemSelectionChangedEvent event;

    @Mock
    private NavigationItem oldItem;

    @Mock
    private NavigationItem newItem;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        event = NavigationItemSelectionChangedEvent.of(oldItem, newItem);
    }

    @Test
    public void getOldItem() throws Exception {
        assertThat(event.getOldItem()).isSameAs(oldItem);
    }

    @Test
    public void getNewItem() throws Exception {
        assertThat(event.getNewItem()).isSameAs(newItem);
    }
}
