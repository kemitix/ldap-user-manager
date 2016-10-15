package net.kemitix.ldapmanager.state;

import net.kemitix.ldapmanager.events.LogMessageAddedEvent;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.reset;

/**
 * Tests for {@link DefaultLogMessages}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class DefaultLogMessagesTest {

    @InjectMocks
    private DefaultLogMessages logMessages;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        logMessages.init();
        reset(eventPublisher);
    }

    @Test
    public void shouldAdd() throws Exception {
        //when
        logMessages.add("redrum");
        //then
        assertThat(logMessages.getMessages()).contains("redrum");
        then(eventPublisher).should()
                            .publishEvent(any(LogMessageAddedEvent.class));
    }

    @Test
    public void shouldGetMessages() throws Exception {
        assertThat(logMessages.getMessages()).hasSize(1);
    }
}
