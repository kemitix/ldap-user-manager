package net.kemitix.ldapmanager.state;

import lombok.val;
import net.kemitix.ldapmanager.events.CurrentContainerChangedEvent;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.ldap.support.LdapNameBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;

/**
 * Tests for {@link DefaultCurrentContainer}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class DefaultCurrentContainerTest {

    private DefaultCurrentContainer container;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        container = new DefaultCurrentContainer(eventPublisher);
        container.init();
    }

    @Test
    public void initShouldUpdateCurrentContainer() {
        then(eventPublisher).should()
                            .publishEvent(any(CurrentContainerChangedEvent.class));
    }

    @Test
    public void initShouldSetDnToEmpty() {
        // the initial DN is empty as it is equal to the base DN and is relative to it
        assertThat(container.getDn()
                            .isEmpty());
    }

    @Test
    public void shouldSetAndGetDn() throws Exception {
        //given
        val dn = LdapNameBuilder.newInstance("ou=users")
                                .build();
        //when
        container.setDn(dn);
        //then
        assertThat(container.getDn()
                            .toString()).isEqualTo("ou=users");
    }
}
