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
 * Tests for {@link ApplicationExitRequest}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class ApplicationExitRequestTest {

    @InjectMocks
    private ApplicationExitRequest request;

    @Mock
    private ApplicationEventPublisher publisher;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldApplicationExitRequestDispatcher() throws Exception {
        //given
        val eventDispatcher = request.applicationExitRequestDispatcher(publisher);
        //when
        eventDispatcher.run();
        //then
        then(publisher).should()
                       .publishEvent(any(ApplicationExitRequest.Event.class));
    }
}
