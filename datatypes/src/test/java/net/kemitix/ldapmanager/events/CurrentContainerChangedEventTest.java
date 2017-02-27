package net.kemitix.ldapmanager.events;

import lombok.val;
import org.junit.Test;

import javax.naming.Name;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link CurrentContainerChangedEvent}.
 */
public class CurrentContainerChangedEventTest {

    @Test
    public void of() throws Exception {
        //given
        val dn = mock(Name.class);
        //when
        val result = CurrentContainerChangedEvent.of(dn);
        //then
        assertThat(result).isInstanceOf(CurrentContainerChangedEvent.class);
        assertThat(result.getNewContainer()).isSameAs(dn);
    }
}
