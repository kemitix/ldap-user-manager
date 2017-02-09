package net.kemitix.ldapmanager;

import net.kemitix.ldapmanager.ldap.LdapAttribute;
import net.trajano.commons.testing.UtilityClassTestUtil;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link Messages}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class MessagesTest {

    @Test
    public void shouldBeUtilityClass() throws Exception {
        UtilityClassTestUtil.assertUtilityClassWellDefined(LdapAttribute.class);
    }

    @Test
    public void valuesOfCoverage() {
        assertThat(Messages.valueOf(Messages.APP_NAME.toString())).isEqualTo(Messages.APP_NAME);
    }
}
