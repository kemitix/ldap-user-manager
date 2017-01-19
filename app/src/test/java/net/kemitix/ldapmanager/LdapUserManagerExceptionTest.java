package net.kemitix.ldapmanager;

import lombok.val;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link LdapUserManagerException}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class LdapUserManagerExceptionTest {

    @Test
    public void shouldCreateAndGetValues() {
        //given
        val message = "the message";
        val cause = new RuntimeException("the cause");
        //when
        val ldapUserManagerException = new LdapUserManagerException(message, cause);
        //then
        assertThat(ldapUserManagerException.getMessage()).isEqualTo(message);
        assertThat(ldapUserManagerException.getCause()).isEqualTo(cause);
    }
}
