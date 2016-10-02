package net.kemitix.ldapmanager.events;

import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;

/**
 * Tests for {@link LogMessageAdded}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class LogMessageAddedTest {

    @InjectMocks
    private LogMessageAdded request;

    @Mock
    private ApplicationEventPublisher publisher;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldLogMessageAddedDispatcher() throws Exception {
        //given
        val eventDispatcher = request.logMessageAddedDispatcher(publisher);
        //when
        eventDispatcher.run();
        //then
        then(publisher).should()
                       .publishEvent(any(ApplicationExitRequest.Event.class));
    }
}
