package net.kemitix.ldapmanager.actions.user.rename;

import lombok.val;
import net.kemitix.ldapmanager.domain.User;
import net.kemitix.ldapmanager.navigation.UserNavigationItem;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link RenameUserRequestEvent}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class RenameUserRequestEventTest {

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldCreateAndGetSameUserBackOut() throws Exception {
        //given
        val userNavigationItem = UserNavigationItem.create(User.builder()
                                                               .build(), applicationEventPublisher);
        //when
        val event = RenameUserRequestEvent.create(userNavigationItem);
        //then
        assertThat(event.getUserNavigationItem()).isSameAs(userNavigationItem);
    }
}
