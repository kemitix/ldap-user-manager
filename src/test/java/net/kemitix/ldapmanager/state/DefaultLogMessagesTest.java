package net.kemitix.ldapmanager.state;

import lombok.val;
import net.kemitix.ldapmanager.events.EventDispatcher;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;
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
    private EventDispatcher logMessageAddedDispatcher;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        logMessages.init();
        reset(logMessageAddedDispatcher);
    }

    @Test
    public void shouldAdd() throws Exception {
        //when
        logMessages.add("redrum");
        //then
        assertThat(logMessages.getMessages()).contains("redrum");
        then(logMessageAddedDispatcher).should()
                                       .run();
    }

    @Test
    public void shouldGetMessages() throws Exception {
        assertThat(logMessages.getMessages()).hasSize(1);
    }

    @Test
    public void getMessagesShouldBeACopy() {
        //given
        val messages = logMessages.getMessages();
        //when
        messages.clear();
        //then
        assertThat(logMessages.getMessages()).hasSize(1);
    }
}
