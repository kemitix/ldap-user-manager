package net.kemitix.ldapmanager.ui;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * Tests for {@link DefaultScheduledExecutorService}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class DefaultScheduledExecutorServiceTest {

    @InjectMocks
    private DefaultScheduledExecutorService executor;

    @Mock
    private UiProperties uiProperties;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldSetPoolSize() throws Exception {
        //given
        given(uiProperties.getScheduledThreadPoolSize()).willReturn(10);
        //when
        executor = new DefaultScheduledExecutorService(uiProperties);
        //then
        assertThat(executor.getCorePoolSize()).isEqualTo(10);
    }
}
