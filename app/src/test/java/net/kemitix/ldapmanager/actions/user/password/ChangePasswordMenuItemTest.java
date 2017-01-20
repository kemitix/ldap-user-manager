package net.kemitix.ldapmanager.actions.user.password;

import net.kemitix.ldapmanager.events.RenameRequestEvent;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

import javax.naming.Name;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;

/**
 * Tests for {@link ChangePasswordMenuItem}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class ChangePasswordMenuItemTest {

    private ChangePasswordMenuItem menuItem;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Mock
    private Name dn;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        menuItem = ChangePasswordMenuItem.create(dn, applicationEventPublisher);
    }

    @Test
    public void shouldGetLabel() throws Exception {
        assertThat(menuItem.getLabel()).isSameAs("Change Password");
    }

    @Test
    public void shouldGetAction() throws Exception {
        //when
        menuItem.getAction()
                .run();
        //then
        then(applicationEventPublisher).should()
                                       .publishEvent(any(RenameRequestEvent.class));
    }

    @Test
    public void createShouldThrowNPEWhenDnIsNull() {
        //given
        exception.expect(NullPointerException.class);
        exception.expectMessage("dn");
        //when
        ChangePasswordMenuItem.create(null, applicationEventPublisher);
    }

    @Test
    public void createShouldThrowNPEWhenPublisherIsNull() {
        //given
        exception.expect(NullPointerException.class);
        exception.expectMessage("applicationEventPublisher");
        //when
        ChangePasswordMenuItem.create(dn, null);
    }
}
