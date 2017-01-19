package net.kemitix.ldapmanager.ui;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * Tests for {@link ClockLabel}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class ClockLabelTest {

    @InjectMocks
    private ClockLabel clockLabel;

    @Mock
    private Supplier<String> timeSupplier;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldUpdateLabel() throws Exception {
        //given
        given(timeSupplier.get()).willReturn("the time");
        //when
        clockLabel.updateLabel();
        //then
        assertThat(clockLabel.getText()).isEqualTo("the time");
    }
}
