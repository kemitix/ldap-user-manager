package net.kemitix.ldapmanager.events;

import lombok.val;
import org.junit.Test;

import javax.naming.Name;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link CreateUserEvent}.
 */
public class CreateUserEventTest {

    @Test
    public void builder() throws Exception {
        //given
        val dn = mock(Name.class);
        //when
        val result = CreateUserEvent.builder()
                                    .dn(dn)
                                    .build();
        //then
        assertThat(result).isInstanceOf(CreateUserEvent.class);
    }

    @Test
    public void of() throws Exception {
        //given
        val dn = mock(Name.class);
        //when
        val result = CreateUserEvent.of(dn);
        //then
        assertThat(result).isInstanceOf(CreateUserEvent.class);
        assertThat(result.getDn()).isSameAs(dn);
    }
}
