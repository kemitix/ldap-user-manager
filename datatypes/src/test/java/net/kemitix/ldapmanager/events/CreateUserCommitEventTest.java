package net.kemitix.ldapmanager.events;

import lombok.val;
import org.assertj.core.api.SoftAssertions;
import org.junit.Test;

import javax.naming.Name;

import static org.mockito.Mockito.mock;

/**
 * Tests for {@link CreateUserCommitEvent}.
 */
public class CreateUserCommitEventTest {

    @Test
    public void of() throws Exception {
        //given
        val dn = mock(Name.class);
        val cn = "cn";
        val sn = "sn";
        //when
        val result = CreateUserCommitEvent.of(dn, cn, sn);
        //then
        SoftAssertions.assertSoftly(s -> {
            s.assertThat(result)
             .isInstanceOf(CreateUserCommitEvent.class);
            s.assertThat(result.getDn())
             .isSameAs(dn);
            s.assertThat(result.getCn())
             .isSameAs(cn);
            s.assertThat(result.getSn())
             .isSameAs(sn);
        });
    }
}
