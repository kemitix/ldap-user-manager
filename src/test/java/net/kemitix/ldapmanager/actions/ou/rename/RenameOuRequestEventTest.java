package net.kemitix.ldapmanager.actions.ou.rename;

import lombok.val;
import net.kemitix.ldapmanager.domain.OU;
import net.kemitix.ldapmanager.navigation.OuNavigationItem;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link RenameOuRequestEvent}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class RenameOuRequestEventTest {

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldCreateAndGetNavigationItemBackOut() {
        //given
        val ouNavigationItem = OuNavigationItem.create(OU.builder()
                                                         .build(), applicationEventPublisher);
        //then
        assertThat(RenameOuRequestEvent.create(ouNavigationItem)
                                       .getOuNavigationItem()).isSameAs(ouNavigationItem);
    }
}
