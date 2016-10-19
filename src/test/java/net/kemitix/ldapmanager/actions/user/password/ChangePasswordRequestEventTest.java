package net.kemitix.ldapmanager.actions.user.password;

import lombok.val;
import net.kemitix.ldapmanager.domain.User;
import net.kemitix.ldapmanager.navigation.UserNavigationItem;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link ChangePasswordRequestEvent}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class ChangePasswordRequestEventTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldCreateAndGetNavigationItemBackOut() throws Exception {
        //when
        val userNavigationItem = UserNavigationItem.create(User.builder()
                                                               .build(), applicationEventPublisher);
        val event = ChangePasswordRequestEvent.create(userNavigationItem);
        //then
        assertThat(event.getUserNavigationItem()).isSameAs(userNavigationItem);
    }

    @Test
    public void shouldThrowNPEWhenNavigationItemIsNull() throws Exception {
        //given
        exception.expect(NullPointerException.class);
        exception.expectMessage("userNavigationItem");
        //when
        ChangePasswordRequestEvent.create(null);
    }
}
