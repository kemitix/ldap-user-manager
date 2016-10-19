package net.kemitix.ldapmanager.actions.user.password;

import org.junit.Test;
import org.springframework.security.authentication.encoding.LdapShaPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link PasswordConfiguration}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class PasswordConfigurationTest {

    @Test
    public void shouldPasswordEncoder() throws Exception {
        assertThat(new PasswordConfiguration().passwordEncoder()).isInstanceOf(LdapShaPasswordEncoder.class);
    }
}
