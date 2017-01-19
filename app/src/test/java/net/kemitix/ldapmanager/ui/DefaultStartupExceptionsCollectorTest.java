package net.kemitix.ldapmanager.ui;

import lombok.val;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link DefaultStartupExceptionsCollector}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class DefaultStartupExceptionsCollectorTest {

    private DefaultStartupExceptionsCollector collector;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        collector = new DefaultStartupExceptionsCollector();
    }

    @Test
    public void addAndGetException() throws Exception {
        //given
        val cause = new Exception("cause");
        //when
        collector.addException("message", cause);
        //then
        assertThat(collector.isEmpty()).isFalse();
        collector.getExceptions()
                 .findFirst()
                 .ifPresent(e -> assertThat(e.getCause()).isSameAs(cause));
    }

    @Test
    public void addExceptionWithNullCauseShouldThrowNPE() {
        //given
        exception.expect(NullPointerException.class);
        //when
        collector.addException("message", null);
    }

    @Test
    public void addExceptionWithNullMessageShouldThrowNPE() {
        //given
        exception.expect(NullPointerException.class);
        val cause = new Exception("cause");
        //when
        collector.addException(null, cause);
    }
}
