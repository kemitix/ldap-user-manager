package net.kemitix.ldapmanager.actions.user.create;

import lombok.val;
import net.kemitix.ldapmanager.events.CreateUserEvent;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

import javax.naming.Name;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;

/**
 * Tests for {@link CreateUserMenuItem}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class CreateUserMenuItemTest {

    private CreateUserMenuItem menuItem;

    @Mock
    private Name dn;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @Captor
    private ArgumentCaptor<Object> objectArgumentCaptor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        menuItem = CreateUserMenuItem.create(dn, applicationEventPublisher);
    }

    @Test
    public void shouldGetLabel() throws Exception {
        assertThat(menuItem.getLabel()).isEqualTo("Create User");
    }

    @Test
    public void shouldGetAction() throws Exception {
        //when
        menuItem.getAction()
                .run();
        //then
        then(applicationEventPublisher).should()
                                       .publishEvent(objectArgumentCaptor.capture());
        val event = objectArgumentCaptor.getValue();
        assertThat(event).isInstanceOf(CreateUserEvent.class);
        val createUserEvent = (CreateUserEvent) event;
        assertThat(createUserEvent.getDn()).isEqualTo(dn);
    }
}
