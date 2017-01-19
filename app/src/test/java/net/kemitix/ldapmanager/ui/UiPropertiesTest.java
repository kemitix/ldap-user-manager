package net.kemitix.ldapmanager.ui;

import lombok.val;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link UiProperties}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class UiPropertiesTest {

    private UiProperties uiProperties;

    @Before
    public void setUp() throws Exception {
        uiProperties = new UiProperties();
    }

    @Test
    public void shouldSetGetScheduledThreadPoolSize() throws Exception {
        //given
        val expected = new Random().nextInt();
        //when
        uiProperties.setScheduledThreadPoolSize(expected);
        //then
        assertThat(uiProperties.getScheduledThreadPoolSize()).isEqualTo(expected);
    }
}
