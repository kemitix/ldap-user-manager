package net.kemitix.ldapmanager.events;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link DefaultDeniableRequestEvent}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class DefaultDeniableRequestEventTest {

    private DefaultDeniableRequestEvent deniableRequestEvent;

    @Before
    public void setUp() throws Exception {
        deniableRequestEvent = new DefaultDeniableRequestEvent();
    }

    @Test
    public void shouldBeApprovedByDefault() throws Exception {
        assertThat(deniableRequestEvent.isApproved()).isTrue();
    }

    @Test
    public void shouldNotBeApprovedOnceDenied() throws Exception {
        //given
        deniableRequestEvent.deny();
        //then
        assertThat(deniableRequestEvent.isApproved()).isFalse();
    }
}
