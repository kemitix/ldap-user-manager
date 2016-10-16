package net.kemitix.ldapmanager.navigation;

import net.kemitix.ldapmanager.domain.OU;
import net.kemitix.ldapmanager.navigation.events.NavigationItemUserSelectedEvent;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;

/**
 * Tests for {@link OuNavigationItem}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class OuNavigationItemTest {

    private NavigationItem ouNavigationItem;

    private OU ou;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    private String name;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        name = "name";
        ou = OU.builder()
               .ou(name)
               .build();
        ouNavigationItem = OuNavigationItem.create(ou, applicationEventPublisher);
    }

    @Test
    public void create() throws Exception {
        assertThat(ouNavigationItem).isInstanceOf(OuNavigationItem.class);
    }

    @Test
    public void run() throws Exception {
        //when
        ouNavigationItem.run();
        //then
        then(applicationEventPublisher).should()
                                       .publishEvent(any(NavigationItemUserSelectedEvent.class));
    }

    @Test
    public void getOu() throws Exception {
        assertThat(((OuNavigationItem) ouNavigationItem).getOu()).isSameAs(ou);
    }

    @Test
    public void toStringContainsName() throws Exception {
        assertThat(ouNavigationItem.toString()).contains(name);
    }
}
