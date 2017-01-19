package net.kemitix.ldapmanager.ui;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link CloseCenterFormEvent}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class CloseCenterFormEventTest {

    @Test
    public void shouldCreate() {
        assertThat(CloseCenterFormEvent.create()).isInstanceOf(CloseCenterFormEvent.class);
    }
}
